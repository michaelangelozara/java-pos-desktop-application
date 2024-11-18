package org.POS.backend.return_purchase;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.product.Product;
import org.POS.backend.purchase.PurchaseDAO;
import org.POS.backend.purchased_item.PurchaseItemDAO;
import org.POS.backend.return_product.ReturnProduct;
import org.POS.backend.stock.Stock;
import org.POS.backend.stock.StockType;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReturnPurchaseService {

    private ReturnPurchaseDAO returnPurchaseDAO;

    private PurchaseDAO purchaseDAO;

    private PurchaseItemDAO purchaseItemDAO;

    private CodeGeneratorService codeGeneratorService;

    private UserDAO userDAO;

    public ReturnPurchaseService(){
        this.returnPurchaseDAO = new ReturnPurchaseDAO();
        this.purchaseDAO = new PurchaseDAO();
        this.purchaseItemDAO = new PurchaseItemDAO();
        this.codeGeneratorService = new CodeGeneratorService();
        this.userDAO = new UserDAO();
    }

    public void add(AddReturnPurchaseRequestDto dto){
        var purchase = this.purchaseDAO.getValidPurchaseById(dto.purchaseId());

        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        if(purchase != null){
            Set<Integer> purchaseItemIds = new HashSet<>();
            for(var purchaseItemDto : dto.returnItems()){
                purchaseItemIds.add(purchaseItemDto.purchaseItemId());
            }

            ReturnPurchase returnPurchase = new ReturnPurchase();
            returnPurchase.setNote(dto.note());
            returnPurchase.setReason(dto.reason());
            returnPurchase.setStatus(dto.status());
            returnPurchase.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.RETURN_PURCHASE_PREFIX));
            returnPurchase.setReturnedAt(LocalDate.now());
            user.addReturnPurchase(returnPurchase);

            UserLog userLog = new UserLog();
            userLog.setCode(returnPurchase.getCode());
            userLog.setDate(LocalDate.now());
            userLog.setAction(UserActionPrefixes.PURCHASE_RETURN_ADD_ACTION_LOG_PREFIX);
            user.addUserLog(userLog);

            List<Product> updatedProducts = new ArrayList<>();

            // update purchase item
            var purchaseItems = this.purchaseItemDAO.getAllValidPurchaseItemByPurchaseItemIds(purchaseItemIds);
            for(int i = 0; i < purchaseItems.size(); i++){
                for(var purchaseItemDto : dto.returnItems()){
                    if(purchaseItems.get(i).getId().equals(purchaseItemDto.purchaseItemId())){
                        purchaseItems.get(i).setReturned(true);
                        purchaseItems.get(i).setReturnedAt(LocalDate.now());
                        purchaseItems.get(i).setReturnPrice(purchaseItemDto.returnPrice());
                        purchaseItems.get(i).setQuantity(purchaseItems.get(i).getQuantity() - purchaseItemDto.returnQuantity());
                        purchaseItems.get(i).setReturnQuantity(purchaseItemDto.returnQuantity());
                        purchaseItems.get(i).setSubtotal(purchaseItems.get(i).getSellingPrice().multiply(BigDecimal.valueOf(purchaseItems.get(i).getQuantity())));

                        Stock stock = new Stock();
                        stock.setDate(LocalDate.now());
                        stock.setStockInOrOut(purchaseItems.get(i).getReturnQuantity());
                        stock.setPrice(purchaseItems.get(i).getReturnPrice());
                        stock.setType(StockType.OUT);
                        stock.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.STOCK_OUT_PREFIX));
                        stock.setPerson(purchase.getPerson());

                        var updatedProduct = purchaseItems.get(i).getProduct();
                        updatedProduct.addStock(stock);
                        updatedProduct.setStock(updatedProduct.getStock() + purchaseItems.get(i).getReturnQuantity());

                        updatedProducts.add(updatedProduct);

                        user.addStock(stock);

//                        purchaseItems.get(i).addReturnPurchase(returnPurchase);
                        returnPurchase.addPurchaseItem(purchaseItems.get(i));
                        break;
                    }
                }
            }

            // update purchase
            purchase.setNetTotal(dto.netTotal());
            purchase.setTotalTax(dto.totalTax());

            purchase.addReturnPurchase(returnPurchase);

            this.returnPurchaseDAO.add(purchase, purchaseItems, userLog, updatedProducts);
        }else{
         throw new RuntimeException("Invalid Purchase");
        }
    }

    public List<ReturnPurchase> getAllValidReturnProduct(int number){
        return this.returnPurchaseDAO.getAlValidReturnProducts(number);
    }

    public List<ReturnPurchase> getAllValidReturnPurchaseByRange(LocalDate start, LocalDate end){
        return this.returnPurchaseDAO.getAllValidReturnPurchaseByRange(start, end);
    }

    public List<ReturnPurchase> getAllValidReturnPurchaseByQuery(String name){
        return this.returnPurchaseDAO.getAllValidReturnPurchaseByQuery(name);
    }

    public ReturnPurchase getValidReturnPurchaseById(int id){
        return returnPurchaseDAO.getValidReturnPurchaseById(id);
    }
}
