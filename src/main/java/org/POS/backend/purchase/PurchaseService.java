package org.POS.backend.purchase;

import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.person.PersonDAO;
import org.POS.backend.person.PersonType;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.purchased_item.*;
import org.POS.backend.user.UserDAO;

import java.math.BigDecimal;
import java.util.*;

public class PurchaseService {

    private PurchaseDAO purchaseDAO;

    private PurchaseMapper purchaseMapper;

    private UserDAO userDAO;

    private PersonDAO personDAO;

    private PurchaseItemMapper purchaseItemMapper;

    private ProductDAO productDAO;

    private PurchaseItemDAO purchaseItemDAO;

    public PurchaseService() {
        this.purchaseDAO = new PurchaseDAO();
        this.purchaseMapper = new PurchaseMapper();
        this.personDAO = new PersonDAO();
        this.purchaseItemMapper = new PurchaseItemMapper();
        this.productDAO = new ProductDAO();
        this.userDAO = new UserDAO();
        this.purchaseItemDAO = new PurchaseItemDAO();
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
            for (var product : products) {
                for (var purchaseItemDto : purchaseItemDtoList) {
                    if (product.getId() == purchaseItemDto.productId()) {
                        var purchaseItem = this.purchaseItemMapper.toPurchaseItem(purchaseItemDto);
                        product.addPurchaseItem(purchaseItem);
                        purchase.addPurchaseItem(purchaseItem);
                        purchaseItemList.add(purchaseItem);
                        break;
                    }
                }
            }

            this.purchaseDAO.add(purchase, purchaseItemList);
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
            List<PurchaseItem> purchaseItems = new ArrayList<>();
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

                        purchaseItems.add(purchaseItem);
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
                        purchaseItems.add(fetchedPurchaseItem);
                        break;
                    }
                }
            }

            // save here
            this.purchaseDAO.update(updatedPurchase);
        }
    }

    public void delete(int purchaseId){
        this.purchaseDAO.delete(purchaseId);
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

}
