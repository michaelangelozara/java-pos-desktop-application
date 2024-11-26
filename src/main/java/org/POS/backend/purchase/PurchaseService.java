package org.POS.backend.purchase;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.person.PersonDAO;
import org.POS.backend.person.PersonType;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.purchased_item.PurchaseItem;
import org.POS.backend.purchased_item.PurchaseItemDAO;
import org.POS.backend.purchased_item.PurchaseItemMapper;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

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

    public void add(AddPurchaseRequestDto dto, Set<PurchaseItem> purchaseItemSet) {
        try {
            var supplier = this.personDAO.getValidPersonByTypeAndId(dto.supplierId(), PersonType.SUPPLIER);
            var admin = this.userDAO.getUserById(CurrentUser.id);

            if (admin == null) throw new RuntimeException("Invalid User");

            if (supplier == null) throw new RuntimeException("Invalid Supplier");

            Purchase purchase = new Purchase();
            purchase.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.PURCHASE_PREFIX));
            purchase.setNote(dto.note());
            purchase.setCreatedDate(LocalDate.now());
            purchase.setPerson(supplier);
            purchase.setUser(admin);

            for (var purchaseItem : purchaseItemSet) {
                purchaseItem.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.PURCHASE_ITEM_PREFIX));
                purchase.addPurchaseItem(purchaseItem);
            }

            UserLog userLog = new UserLog();
            userLog.setCode(purchase.getCode());
            userLog.setDate(LocalDate.now());
            userLog.setAction(UserActionPrefixes.PURCHASES_ADD_ACTION_LOG_PREFIX);
            admin.addUserLog(userLog);

            this.purchaseDAO.add(purchase, userLog);
        }catch (Exception e){
            throw e;
        }
    }

    public void update(UpdatePurchaseRequestDto dto, Set<PurchaseItem> purchaseItems) {
        var purchase = this.purchaseDAO.getValidPurchaseById(dto.purchaseId());
        var user = this.userDAO.getUserById(CurrentUser.id);

        if (purchase == null) throw new RuntimeException("Invalid Purchase");

        if (user == null) throw new RuntimeException("Invalid User");
        CopyOnWriteArrayList<PurchaseItem> oldPurchaseItems = new CopyOnWriteArrayList<>(purchase.getPurchaseItems());
        for (var oldPurchaseItem : oldPurchaseItems) {
            boolean isEqualed = false;
            for (var purchaseItem : purchaseItems) {
                if (purchaseItem.getId() == null) {
                    purchaseItem.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.PURCHASE_ITEM_PREFIX));
                    purchase.addPurchaseItem(purchaseItem);
                    continue;
                }

                if (oldPurchaseItem.getId().equals(purchaseItem.getId())) {
                    isEqualed = true;
                    break;
                }
            }

            if (!isEqualed) {
                if (oldPurchaseItem.isDeleted())
                    continue;
                oldPurchaseItem.setDeleted(true);
                oldPurchaseItem.setDeletedAt(LocalDate.now());
            }
        }

        purchase.setNote(dto.note());

        UserLog userLog = new UserLog();
        userLog.setCode(purchase.getCode());
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.PURCHASES_ADD_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        this.purchaseDAO.update(purchase, userLog);
    }

    public void delete(int purchaseId) {
        var user = this.userDAO.getUserById(CurrentUser.id);
        if (user == null)
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

    public List<Purchase> getAllValidPurchasesWithoutLimit() {
        return this.purchaseDAO.getAllValidPurchasesWithoutLimit();
    }

    public Purchase getValidPurchaseWithoutDto(int id) {
        return this.purchaseDAO.getValidPurchaseById(id);
    }

    public List<PurchaseResponseDto> getAllValidPurchaseByRangeAndSupplierId(LocalDate start, LocalDate end, int id) {
        return this.purchaseMapper.toPurchaseResponseDtoList(this.purchaseDAO.getAllValidPurchaseByRangeAndSupplierId(start, end, id));
    }

    public List<Purchase> getAllValidPurchaseByCodeAndSupplierId(String query, int id) {
        return this.purchaseDAO.getAllValidPurchasesByCodeAndSupplierId(query, id);
    }

}
