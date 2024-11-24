package org.POS.backend.purchase;

public record UpdatePurchaseRequestDto(
        int purchaseId,
        String note
) {
}
