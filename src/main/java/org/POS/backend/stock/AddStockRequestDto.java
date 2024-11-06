package org.POS.backend.stock;

import java.math.BigDecimal;

public record AddStockRequestDto(
        int stockInOrOut,
        BigDecimal price,
        int personId,
        StockType type
) {
}
