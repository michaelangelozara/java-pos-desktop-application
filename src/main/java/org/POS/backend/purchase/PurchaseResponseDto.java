package org.POS.backend.purchase;

import org.POS.backend.person.PersonResponseDto;
import org.POS.backend.purchased_product.PurchaseProductResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PurchaseResponseDto(
        int id,
        String code,
        LocalDate date,
        PersonResponseDto supplier,
        BigDecimal subtotal,
        BigDecimal transport,
        BigDecimal discount,
        BigDecimal netTotal,
        BigDecimal totalPaid,
        BigDecimal totalDue,
        PurchaseStatus status,
        List<PurchaseProductResponseDto> purchaseProducts
) {
}
