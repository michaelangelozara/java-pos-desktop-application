package org.POS.backend.inventory_adjustment;

import org.POS.backend.product.Product;
import org.POS.backend.user.User;

import java.time.LocalDate;

public record InventoryAdjustmentResponseDto(
        int id,
        Product product,
        User user,
        String code,
        String reason,
        LocalDate date,
        InventoryAdjustmentStatus status,
        InventoryAdjustmentType type,
        String note,
        int quantity
) {
}
