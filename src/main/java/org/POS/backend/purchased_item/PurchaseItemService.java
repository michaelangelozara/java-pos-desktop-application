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
//        List<PurchaseProduct> purchaseItems = new ArrayList<>();
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
//                    purchaseItems.add(purchaseProduct);
//                    break;
//                }
//            }
//        }
//        return GlobalVariable.PURCHASE_PRODUCT_ADDED;
//    }

//    public void update(List<UpdatePurchaseProductRequestDto> purchaseItems, Purchase purchase){
//        List<PurchaseItem> prurchaseProductList = new ArrayList<>();
//        for(int i = 0 ; i < purchaseItems.size(); i++){
//            var purchaseProduct = new PurchaseItem();
//            purchaseProduct.setId(purchaseItems.get(i).purchaseItemId() == null ? null : purchaseItems.get(i).purchaseItemId());
//            purchaseProduct.setQuantity(purchaseItems.get(i).quantity());
//            purchaseProduct.setPurchasePrice(purchaseItems.get(i).purchasePrice());
//            purchaseProduct.setSellingPrice(purchaseItems.get(i).sellingPrice());
//            purchaseProduct.setTax(purchaseItems.get(i).taxValue());
//            purchaseProduct.setSubtotal(purchaseItems.get(i).subtotal());
//            purchaseProduct.setProductCode(purchaseItems.get(i).productCode());
//            prurchaseProductList.add(purchaseProduct);
//        }
//
//        List<String> codes = new ArrayList<>();
//        for(int i = 0; i < purchaseItems.size(); i++){
//            codes.add(purchaseItems.get(i).productCode());
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
