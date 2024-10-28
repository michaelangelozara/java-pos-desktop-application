package org.POS.backend.product;

import java.math.BigDecimal;

public record InventoryResponse(
        String image,
        String productName,
        String productCode,
        String productModel,
        int stock,
        BigDecimal purchasePriceAVG,
        BigDecimal sellingPrice,
        BigDecimal inventoryValue,
        ProductStatus status
) {
}
