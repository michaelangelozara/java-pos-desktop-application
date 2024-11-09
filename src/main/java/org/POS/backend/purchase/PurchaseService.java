package org.POS.backend.purchase;

import jakarta.transaction.Transactional;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.person.PersonDAO;
import org.POS.backend.person.PersonType;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.purchased_product.PurchaseProduct;
import org.POS.backend.purchased_product.PurchaseProductMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PurchaseService {

    private PurchaseDAO purchaseDAO;

    private PurchaseMapper purchaseMapper;

    private PersonDAO personDAO;

    private PurchaseProductMapper purchaseProductMapper;

    private ProductDAO productDAO;

    public PurchaseService() {
        this.purchaseDAO = new PurchaseDAO();
        this.purchaseMapper = new PurchaseMapper();
        this.personDAO = new PersonDAO();
        this.purchaseProductMapper = new PurchaseProductMapper();
        this.productDAO = new ProductDAO();
    }

    @Transactional
    public String add(AddPurchaseRequestDto dto) {
        var supplier = this.personDAO.getValidPersonByTypeAndId(dto.supplierId(), PersonType.SUPPLIER);

        if (supplier == null) {
            return GlobalVariable.PERSON_NOT_FOUND;
        }

        var purchase = this.purchaseMapper.toPurchase(dto, supplier);

        Set<Integer> productIds = new HashSet<>();
        for(var purchaseProduct : dto.purchaseProducts()){
            productIds.add(purchaseProduct.productId());
        }

        var products = this.productDAO.getAllValidProductsByProductIds(productIds);

        List<PurchaseProduct> purchaseProducts = new ArrayList<>();
        for(var purchaseProductRequestDto : dto.purchaseProducts()){
            for(var product : products){
                if(product.getId() == purchaseProductRequestDto.productId()){
                    PurchaseProduct purchaseProduct = this.purchaseProductMapper.toPurchaseProduct(purchaseProductRequestDto);
                    purchaseProduct.setPurchase(purchase);
                    purchase.addPurchaseProduct(purchaseProduct);
                    purchaseProduct.setProduct(product);
                    purchaseProducts.add(purchaseProduct);
                    break;
                }
            }

        }
        // implement the add purchase along with its children here


//        var savedPurchase = this.purchaseDAO.add(purchase);
        // Pass the saved purchase and purchase product DTOs to be added as part of the same transaction
//        return purchaseProductService.add(savedPurchase, dto.purchaseProducts());
        return "";
    }

    public void update(UpdatePurchaseRequestDto dto){
        var purchase = this.purchaseDAO.getValidPurchaseById(dto.purchaseId());
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
