package org.POS.backend.inventory_adjustment;

import org.POS.backend.product_attribute.ProductAttribute;

import java.util.List;

public record AddInventoryAdjustmentDtoForVariableProduct(
        int productId,
        String reason,
        InventoryAdjustmentType type,
        List<ProductAttribute> productAttributes
) {
}
