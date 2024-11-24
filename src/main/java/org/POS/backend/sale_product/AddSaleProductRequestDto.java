package org.POS.backend.sale_product;

import java.math.BigDecimal;

public record AddSaleProductRequestDto(
        int productId,
        BigDecimal price,
        int quantity,
        Integer variationId
) {
}
