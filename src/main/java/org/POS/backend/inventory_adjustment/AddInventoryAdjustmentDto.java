package org.POS.backend.inventory_adjustment;

public record AddInventoryAdjustmentDto(
        int productId,
        String reason,
        String note,
        InventoryAdjustmentStatus status,
        InventoryAdjustmentType type,
        int quantity
) {
}
