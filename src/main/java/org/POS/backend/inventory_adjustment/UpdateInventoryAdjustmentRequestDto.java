package org.POS.backend.inventory_adjustment;

public record UpdateInventoryAdjustmentRequestDto(
        int id,
        String reason,
        InventoryAdjustmentType type,
        int quantity,
        String note
) {
}
