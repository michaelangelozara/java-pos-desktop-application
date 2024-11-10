package org.POS.backend.purchase;

import jakarta.transaction.Transactional;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.person.PersonDAO;
import org.POS.backend.person.PersonType;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.purchased_item.AddPurchaseItemRequestDto;
import org.POS.backend.purchased_item.PurchaseItem;
import org.POS.backend.purchased_item.PurchaseItemMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PurchaseService {

    private PurchaseDAO purchaseDAO;

    private PurchaseMapper purchaseMapper;

    private PersonDAO personDAO;

    private PurchaseItemMapper purchaseItemMapper;

    private ProductDAO productDAO;

    public PurchaseService() {
        this.purchaseDAO = new PurchaseDAO();
        this.purchaseMapper = new PurchaseMapper();
        this.personDAO = new PersonDAO();
        this.purchaseItemMapper = new PurchaseItemMapper();
        this.productDAO = new ProductDAO();
    }

    @Transactional
    public String add(AddPurchaseRequestDto dto, Set<AddPurchaseItemRequestDto> purchaseItemDtoList) {
        var supplier = this.personDAO.getValidPersonByTypeAndId(dto.supplierId(), PersonType.SUPPLIER);

        if(supplier != null){
            Set<Integer> productIds = new HashSet<>();
            for(var purchaseItem : purchaseItemDtoList){
                productIds.add(purchaseItem.productId());
            }

            Purchase purchase = this.purchaseMapper.toPurchase(dto);
            supplier.addPurchase(purchase);

            var products = this.productDAO.getAllValidProductsByProductIds(productIds);
            List<PurchaseItem> purchaseItemList = new ArrayList<>();
            for(var product : products){
                for(var purchaseItemDto : purchaseItemDtoList){
                    if(product.getId() == purchaseItemDto.productId()){
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

    public void update(UpdatePurchaseRequestDto dto){
//        var purchase = this.purchaseDAO.getValidPurchaseById(dto.purchaseId());
//
//        if(purchase != null){
//            var updatedPurchase = this.purchaseMapper.toUpdatedPurchase(purchase, dto);
//            this.purchaseDAO.update(updatedPurchase);
//            purchaseProductService.update(dto.purchaseProducts(), updatedPurchase);
//        }
    }

    public PurchaseResponseDto getValidPurchaseByPurchaseId(int purchaseId){
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
