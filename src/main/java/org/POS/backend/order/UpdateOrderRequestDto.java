package org.POS.backend.order;

import org.POS.backend.return_product.AddReturnItemRequestDto;

import java.util.List;

public record UpdateOrderRequestDto(
        int orderId,
        List<AddReturnItemRequestDto> returnItems,
        String note,
        String reason
) {
}
