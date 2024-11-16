package org.POS.backend.order;

import java.util.List;

public record UpdateOrderRequestDto(
        int orderId,
        List<Integer> returnedProductId,
        String note,
        String deliveryAddress,
        String reason
) {
}
