package org.POS.backend.quotation;

import java.math.BigDecimal;

public record AddQuotationRequestDto(
        int clientId,
        BigDecimal totalTax,
        BigDecimal subtotalTax,
        BigDecimal netSubtotal,
        BigDecimal discount,
        BigDecimal transportCost,
        String note,
        QuotationStatus status
) {
}
