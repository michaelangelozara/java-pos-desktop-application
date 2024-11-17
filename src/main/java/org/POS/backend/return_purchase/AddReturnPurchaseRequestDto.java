package org.POS.backend.return_purchase;

import org.POS.backend.return_purchase.return_item.AddReturnItemRequestDto;

import java.math.BigDecimal;
import java.util.Set;

public record AddReturnPurchaseRequestDto(
        int purchaseId,
        Set<AddReturnItemRequestDto> returnItems,
        String reason,
        BigDecimal totalTax,
        BigDecimal netTotal,
        String note,
        ReturnPurchaseStatus status
) {
}
