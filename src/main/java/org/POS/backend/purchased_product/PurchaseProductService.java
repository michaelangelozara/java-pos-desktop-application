package org.POS.backend.purchased_product;

import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.product.Product;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.purchase.Purchase;
import org.POS.backend.purchase.PurchaseDAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PurchaseProductService {

    private PurchaseProductDAO purchaseProductDAO;

    private PurchaseDAO purchaseDAO;

    private ProductDAO productDAO;

    public PurchaseProductService() {
        this.purchaseProductDAO = new PurchaseProductDAO();
        this.purchaseDAO = new PurchaseDAO();
        this.productDAO = new ProductDAO();
    }

    public String add(
            Purchase purchase,
            List<AddPurchaseProductRequestDto> addPurchaseProductRequestDtoList
    ) {
        var getNewPurchase = this.purchaseDAO.getValidPurchaseById(purchase.getId());

        Set<Integer> productIds = new HashSet<>();
        for (var addPurchaseProductRequestDto : addPurchaseProductRequestDtoList) {
            productIds.add(addPurchaseProductRequestDto.productId());
        }

        if (getNewPurchase != null) {
            Set<Product> products = this.productDAO.getAllValidProductsByProductIds(productIds);
            List<PurchaseProduct> purchaseProducts = new ArrayList<>();

            for (var product : products) {
                for (var addPurchaseProductRequestDto : addPurchaseProductRequestDtoList) {
                    if(product.getId() == addPurchaseProductRequestDto.productId()){
                        PurchaseProduct purchaseProduct = new PurchaseProduct();
                        purchaseProduct.setProduct(product);
                        purchaseProduct.setPurchase(purchase);
                        purchaseProduct.setQuantity(addPurchaseProductRequestDto.quantity());
                        purchaseProduct.setPurchasePrice(addPurchaseProductRequestDto.purchasePrice());

                        purchaseProducts.add(purchaseProduct);
                        break;
                    }
                }
            }
            this.purchaseProductDAO.add(purchaseProducts);
            return GlobalVariable.PURCHASE_PRODUCT_ADDED;
        }
        return GlobalVariable.PURCHASE_NOT_FOUND;
    }
}
