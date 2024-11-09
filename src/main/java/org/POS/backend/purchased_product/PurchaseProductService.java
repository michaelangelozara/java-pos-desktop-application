package org.POS.backend.purchased_product;

import jakarta.transaction.Transactional;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.product.Product;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.purchase.Purchase;
import org.POS.backend.purchase.PurchaseDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PurchaseProductService {

    private PurchaseProductDAO purchaseProductDAO;

    private PurchaseDAO purchaseDAO;

    private ProductDAO productDAO;

    private PurchaseProductMapper purchaseProductMapper;

    public PurchaseProductService() {
        this.purchaseProductDAO = new PurchaseProductDAO();
        this.purchaseDAO = new PurchaseDAO();
        this.productDAO = new ProductDAO();
        this.purchaseProductMapper = new PurchaseProductMapper();
    }

//    public String add(Purchase purchase, Set<AddPurchaseProductRequestDto> addPurchaseProductRequestDtoList) {
//        var getNewPurchase = this.purchaseDAO.getValidPurchaseById(purchase.getId());
//
//        if (getNewPurchase == null) {
//            return GlobalVariable.PURCHASE_NOT_FOUND;
//        }
//
//        Set<Integer> productIds = addPurchaseProductRequestDtoList.stream()
//                .map(AddPurchaseProductRequestDto::productId)
//                .collect(Collectors.toSet());
//
//        Set<Product> products = this.productDAO.getAllValidProductsByProductIds(productIds);
//        List<PurchaseProduct> purchaseProducts = new ArrayList<>();
//
//        // Build purchase-product relationships
//        for (var product : products) {
//            for (var dto : addPurchaseProductRequestDtoList) {
//                if (product.getId().equals(dto.productId())) {
//                    var purchaseProduct = this.purchaseProductMapper.toPurchaseProduct(dto);
//                    purchase.addPurchaseProduct(purchaseProduct);
//                    product.addPurchaseProduct(purchaseProduct);
//
//                    purchaseProduct.setProduct(product);
//                    purchaseProduct.setPurchase(purchase);
//                    purchaseProducts.add(purchaseProduct);
//                    break;
//                }
//            }
//        }
//        return GlobalVariable.PURCHASE_PRODUCT_ADDED;
//    }

    public void update(List<UpdatePurchaseProductRequestDto> purchaseProducts, Purchase purchase){
        List<PurchaseProduct> prurchaseProductList = new ArrayList<>();
        for(int i = 0 ; i < purchaseProducts.size(); i++){
            var purchaseProduct = new PurchaseProduct();
            purchaseProduct.setId(purchaseProducts.get(i).purchaseProductId() == null ? null : purchaseProducts.get(i).purchaseProductId());
            purchaseProduct.setQuantity(purchaseProducts.get(i).quantity());
            purchaseProduct.setPurchasePrice(purchaseProducts.get(i).purchasePrice());
            purchaseProduct.setSellingPrice(purchaseProducts.get(i).sellingPrice());
            purchaseProduct.setTax(purchaseProducts.get(i).taxValue());
            purchaseProduct.setSubtotal(purchaseProducts.get(i).subtotal());
            purchaseProduct.setProductCode(purchaseProducts.get(i).productCode());
            prurchaseProductList.add(purchaseProduct);
        }

        List<String> codes = new ArrayList<>();
        for(int i = 0; i < purchaseProducts.size(); i++){
            codes.add(purchaseProducts.get(i).productCode());
        }

        var products = this.productDAO.getAllValidProductByProductCode(codes);

        for(var product : products){
            for(var purchaseProduct : prurchaseProductList){
                if(product.getCode().equals(purchaseProduct.getProductCode())){
                    purchaseProduct.setProduct(product);
                }
            }
        }

        for(int i = 0; i < prurchaseProductList.size(); i++){
            var purchaseProduct = prurchaseProductList.get(i);
            purchaseProduct.setPurchase(purchase);
            purchase.addPurchaseProduct(purchaseProduct);
        }

        this.purchaseProductDAO.update(prurchaseProductList);
    }

    @Transactional
    public void deletePurchaseProductByPurchaseProductId(int purchaseProductId){
        this.purchaseProductDAO.delete(purchaseProductId);
    }
}
