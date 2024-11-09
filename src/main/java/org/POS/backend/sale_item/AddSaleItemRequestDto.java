package org.POS.backend.sale_item;

import java.math.BigDecimal;

public record AddSaleItemRequestDto(
        int productId,
        BigDecimal price,
        int quantity,
        BigDecimal subtotal
) {
}
