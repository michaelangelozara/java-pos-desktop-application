package org.POS.backend.order;

import java.math.BigDecimal;
import java.util.List;

public record UpdateOrderRequestDto(
        int orderId,
        List<Integer> returnedProductIds,
        String note,
        String deliveryAddress,
        String reason,
        BigDecimal amount
) {
}
