package org.POS.backend.return_purchase;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.purchase.PurchaseDAO;
import org.POS.backend.purchased_item.PurchaseItemDAO;
import org.POS.backend.return_product.ReturnProduct;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;

import java.math.BigDecimal;
import java.time.LocalDate;
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

            UserLog userLog = new UserLog();
            userLog.setCode(returnPurchase.getCode());
            userLog.setDate(LocalDate.now());
            userLog.setAction(UserActionPrefixes.PURCHASE_RETURN_ADD_ACTION_LOG_PREFIX);
            user.addUserLog(userLog);

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

                        purchaseItems.get(i).addReturnPurchase(returnPurchase);
                        returnPurchase.addPurchaseItem(purchaseItems.get(i));
                        break;
                    }
                }
            }

            // update purchase
            purchase.setNetTotal(dto.netTotal());
            purchase.setTotalTax(dto.totalTax());

            purchase.addReturnPurchase(returnPurchase);

            this.returnPurchaseDAO.add(purchase, returnPurchase, purchaseItems, userLog);
        }else{
         throw new RuntimeException("Invalid Purchase");
        }
    }

    public List<ReturnPurchase> getAllValidReturnProduct(int number){
        return this.returnPurchaseDAO.getAlValidReturnProducts(number);
    }
}
