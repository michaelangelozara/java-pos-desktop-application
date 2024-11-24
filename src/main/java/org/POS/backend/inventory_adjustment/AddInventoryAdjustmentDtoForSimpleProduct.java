package org.POS.backend.inventory_adjustment;

public record AddInventoryAdjustmentDtoForSimpleProduct(
        int productId,
        String reason,
        InventoryAdjustmentType type,
        int quantity
) {
}
