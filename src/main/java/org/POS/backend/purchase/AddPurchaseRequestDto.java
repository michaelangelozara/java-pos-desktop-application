package org.POS.backend.purchase;

public record AddPurchaseRequestDto(
        int supplierId,
        String note
) {
}
