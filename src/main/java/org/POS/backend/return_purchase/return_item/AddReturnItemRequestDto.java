package org.POS.backend.return_purchase.return_item;

import java.math.BigDecimal;

public record AddReturnItemRequestDto(
        int purchaseItemId,
        int returnQuantity,
        BigDecimal returnPrice
) {
}
