package org.POS.backend.purchase;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.person.PersonDAO;
import org.POS.backend.person.PersonType;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.purchased_item.*;
import org.POS.backend.stock.Stock;
import org.POS.backend.stock.StockType;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;

import java.time.LocalDate;
import java.util.*;

public class PurchaseService {

    private PurchaseDAO purchaseDAO;

    private PurchaseMapper purchaseMapper;

    private UserDAO userDAO;

    private PersonDAO personDAO;

    private PurchaseItemMapper purchaseItemMapper;

    private ProductDAO productDAO;

    private PurchaseItemDAO purchaseItemDAO;

    private CodeGeneratorService codeGeneratorService;

    public PurchaseService() {
        this.purchaseDAO = new PurchaseDAO();
        this.purchaseMapper = new PurchaseMapper();
        this.personDAO = new PersonDAO();
        this.purchaseItemMapper = new PurchaseItemMapper();
        this.productDAO = new ProductDAO();
        this.userDAO = new UserDAO();
        this.purchaseItemDAO = new PurchaseItemDAO();
        this.codeGeneratorService = new CodeGeneratorService();
    }

    public String add(AddPurchaseRequestDto dto, Set<AddPurchaseItemRequestDto> purchaseItemDtoList) {
        var supplier = this.personDAO.getValidPersonByTypeAndId(dto.supplierId(), PersonType.SUPPLIER);
        var user = this.userDAO.getUserById(CurrentUser.id);

        if (supplier != null && user != null) {
            Set<Integer> productIds = new HashSet<>();
            for (var purchaseItem : purchaseItemDtoList) {
                productIds.add(purchaseItem.productId());
            }

            Purchase purchase = this.purchaseMapper.toPurchase(dto);
            supplier.addPurchase(purchase);
            user.addPurchase(purchase);

            var products = this.productDAO.getAllValidProductsByProductIds(productIds);
            List<PurchaseItem> purchaseItemList = new ArrayList<>();
            List<Stock> stocks = new ArrayList<>();
            for (var product : products) {
                for (var purchaseItemDto : purchaseItemDtoList) {
                    if (product.getId() == purchaseItemDto.productId()) {
                        var purchaseItem = this.purchaseItemMapper.toPurchaseItem(purchaseItemDto);
                        product.addPurchaseItem(purchaseItem);
                        purchase.addPurchaseItem(purchaseItem);
                        purchaseItemList.add(purchaseItem);

                        Stock stock = new Stock();
                        stock.setDate(LocalDate.now());
                        stock.setStockInOrOut(purchaseItem.getQuantity());
                        stock.setPrice(purchaseItem.getSubtotal());
                        stock.setType(StockType.IN);
                        stock.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.STOCK_IN_PREFIX));

                        user.addStock(stock);
                        supplier.addStock(stock);
                        product.addStock(stock);

                        product.setStock(product.getStock() + purchaseItem.getQuantity());
                        break;
                    }
                }
            }

            UserLog userLog = new UserLog();
            userLog.setCode(purchase.getCode());
            userLog.setDate(LocalDate.now());
            userLog.setAction(UserActionPrefixes.PURCHASES_ADD_ACTION_LOG_PREFIX);
            user.addUserLog(userLog);

            this.purchaseDAO.add(purchase, purchaseItemList, userLog, products);
        }
        return GlobalVariable.PURCHASE_ADDED;
    }

    public void update(UpdatePurchaseRequestDto dto) {
        var purchase = this.purchaseDAO.getValidPurchaseById(dto.purchaseId());
        var user = this.userDAO.getUserById(CurrentUser.id);

        if (purchase != null && user != null) {
            var updatedPurchase = this.purchaseMapper.toUpdatedPurchase(purchase, dto);

            Set<String> productCodes = new HashSet<>();
            List<UpdatePurchaseItemRequestDto> newPurchaseItemDtoList = new ArrayList<>();
            for (var purchaseItemDto : dto.purchaseItems()) {
                if (purchaseItemDto.purchaseItemId() == null) {
                    productCodes.add(purchaseItemDto.productCode());
                    newPurchaseItemDtoList.add(purchaseItemDto);
                }
            }

            // get all the products that equaled to products' codes
            var products = this.productDAO.getAllValidProductByProductCode(productCodes);
            // iterate all the product and save the purchase item
            for (var product : products) {
                for (var purchaseItemDto : newPurchaseItemDtoList) {
                    if (product.getCode().equals(purchaseItemDto.productCode())) {
                        PurchaseItem purchaseItem = new PurchaseItem();
                        purchaseItem.setQuantity(purchaseItemDto.quantity());
                        purchaseItem.setPurchasePrice(purchaseItemDto.purchasePrice());
                        purchaseItem.setSellingPrice(purchaseItemDto.sellingPrice());
                        purchaseItem.setTax(purchaseItemDto.taxValue());
                        purchaseItem.setSubtotal(purchaseItemDto.subtotal());

                        product.addPurchaseItem(purchaseItem);
                        updatedPurchase.addPurchaseItem(purchaseItem);

                        break;
                    }
                }
            }

            Set<Integer> purchaseItemsIds = new HashSet<>();
            // filter all existing purchase item
            for (var purchaseItemDto : dto.purchaseItems()) {
                if (purchaseItemDto.purchaseItemId() != null) {
                    purchaseItemsIds.add(purchaseItemDto.purchaseItemId());
                }
            }

            List<PurchaseItem> updatedPurchaseItems = new ArrayList<>();

            // fetch all valid purchase items
            var fetchedPurchaseItems = this.purchaseItemDAO.getAllValidPurchaseItemByPurchaseItemIds(purchaseItemsIds);
            for (var fetchedPurchaseItem : fetchedPurchaseItems) {
                for (var purchaseItemDto : dto.purchaseItems()) {
                    if (Objects.equals(fetchedPurchaseItem.getId(), purchaseItemDto.purchaseItemId())) {
                        fetchedPurchaseItem.setQuantity(purchaseItemDto.quantity());
                        fetchedPurchaseItem.setPurchasePrice(purchaseItemDto.purchasePrice());
                        fetchedPurchaseItem.setSellingPrice(purchaseItemDto.sellingPrice());
                        fetchedPurchaseItem.setTax(purchaseItemDto.taxValue());
                        fetchedPurchaseItem.setSubtotal(purchaseItemDto.subtotal());
                        updatedPurchaseItems.add(fetchedPurchaseItem);
                        break;
                    }
                }
            }

            UserLog userLog = new UserLog();
            userLog.setCode(updatedPurchase.getCode());
            userLog.setDate(LocalDate.now());
            userLog.setAction(UserActionPrefixes.PURCHASES_EDIT_ACTION_LOG_PREFIX);
            user.addUserLog(userLog);

            // save here
            this.purchaseDAO.update(updatedPurchase, updatedPurchaseItems, userLog);
        }
    }

    public void delete(int purchaseId) {
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        UserLog userLog = new UserLog();
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.PURCHASES_REMOVE_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);
        this.purchaseDAO.delete(purchaseId, userLog);
    }

    public PurchaseResponseDto getValidPurchaseByPurchaseId(int purchaseId) {
        return this.purchaseMapper.toPurchaseResponseDto(this.purchaseDAO.getValidPurchaseById(purchaseId));
    }

    public List<PurchaseResponseDto> getAllValidPurchases() {
        return this.purchaseMapper.toPurchaseResponseDtoList(this.purchaseDAO.getAllValidPurchases());
    }

    public List<PurchaseResponseDto> getAllValidPurchaseBySupplierId(int supplierId) {
        return
                this.purchaseMapper
                        .toPurchaseResponseDtoList(this.purchaseDAO.getAllValidPurchaseBySupplierId(supplierId));
    }

    public List<PurchaseResponseDto> getAllValidPurchaseByCodeAndSupplierName(String query) {
        return this.purchaseMapper.toPurchaseResponseDtoList(this.purchaseDAO.getAllValidPurchasesByCodeAndSupplier(query));
    }

    public List<PurchaseResponseDto> getAllValidPurchaseByRange(LocalDate start, LocalDate end) {
        return this.purchaseMapper.toPurchaseResponseDtoList(this.purchaseDAO.getAllValidPurchaseByRange(start, end));
    }

    public List<Purchase> getAllValidPurchasesWithoutLimit(){
        return this.purchaseDAO.getAllValidPurchasesWithoutLimit();
    }

    public Purchase getValidPurchaseWithoutDto(int id){
        return this.purchaseDAO.getValidPurchaseById(id);
    }

    public List<PurchaseResponseDto> getAllValidPurchaseByRangeAndSupplierId(LocalDate start, LocalDate end, int id){
        return this.purchaseMapper.toPurchaseResponseDtoList(this.purchaseDAO.getAllValidPurchaseByRangeAndSupplierId(start, end, id));
    }

}
