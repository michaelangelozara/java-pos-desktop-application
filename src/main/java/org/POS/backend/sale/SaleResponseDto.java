package org.POS.backend.sale;

import org.POS.backend.invoice.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaleResponseDto(
        int saleId,
        String username,
        String code,
        String client,
        BigDecimal netTotal,
        LocalDate saleDate,
        Invoice invoice
) {
}
