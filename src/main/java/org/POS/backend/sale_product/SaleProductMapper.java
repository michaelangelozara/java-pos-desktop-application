package org.POS.backend.sale_product;

import org.POS.backend.product.Product;

import java.math.BigDecimal;

public class SaleProductMapper {

    public SaleProduct toSaleItem(AddSaleProductRequestDto dto, Product product){
        SaleProduct saleProduct = new SaleProduct();
        saleProduct.setQuantity(dto.quantity());
        saleProduct.setPrice(product.getSellingPrice());
        saleProduct.setSubtotal(product.getSellingPrice().multiply(BigDecimal.valueOf(dto.quantity())));
        return saleProduct;
    }
}
