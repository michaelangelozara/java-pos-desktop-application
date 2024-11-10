package org.POS.backend.purchased_item;

import jakarta.transaction.Transactional;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.purchase.PurchaseDAO;

public class PurchaseItemService {



    private PurchaseDAO purchaseDAO;

    private ProductDAO productDAO;

    private PurchaseItemMapper purchaseItemMapper;

    public PurchaseItemService() {
        this.purchaseDAO = new PurchaseDAO();
        this.productDAO = new ProductDAO();
        this.purchaseItemMapper = new PurchaseItemMapper();
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

//    public void update(List<UpdatePurchaseProductRequestDto> purchaseProducts, Purchase purchase){
//        List<PurchaseItem> prurchaseProductList = new ArrayList<>();
//        for(int i = 0 ; i < purchaseProducts.size(); i++){
//            var purchaseProduct = new PurchaseItem();
//            purchaseProduct.setId(purchaseProducts.get(i).purchaseProductId() == null ? null : purchaseProducts.get(i).purchaseProductId());
//            purchaseProduct.setQuantity(purchaseProducts.get(i).quantity());
//            purchaseProduct.setPurchasePrice(purchaseProducts.get(i).purchasePrice());
//            purchaseProduct.setSellingPrice(purchaseProducts.get(i).sellingPrice());
//            purchaseProduct.setTax(purchaseProducts.get(i).taxValue());
//            purchaseProduct.setSubtotal(purchaseProducts.get(i).subtotal());
//            purchaseProduct.setProductCode(purchaseProducts.get(i).productCode());
//            prurchaseProductList.add(purchaseProduct);
//        }
//
//        List<String> codes = new ArrayList<>();
//        for(int i = 0; i < purchaseProducts.size(); i++){
//            codes.add(purchaseProducts.get(i).productCode());
//        }
//
//        var products = this.productDAO.getAllValidProductByProductCode(codes);
//
//        for(var product : products){
//            for(var purchaseProduct : prurchaseProductList){
//                if(product.getCode().equals(purchaseProduct.getProductCode())){
//                    purchaseProduct.setProduct(product);
//                }
//            }
//        }
//
//        for(int i = 0; i < prurchaseProductList.size(); i++){
//            var purchaseProduct = prurchaseProductList.get(i);
//            purchaseProduct.setPurchase(purchase);
//            purchase.addPurchaseItem(purchaseProduct);
//        }
//
//        this.purchaseProductDAO.update(prurchaseProductList);
//    }

    @Transactional
    public void deletePurchaseProductByPurchaseProductId(int purchaseProductId){
    }
}
