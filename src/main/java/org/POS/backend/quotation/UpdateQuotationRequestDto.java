package org.POS.backend.quotation;

import org.POS.backend.quoted_item.UpdateQuotedItemRequestDto;

import java.math.BigDecimal;
import java.util.List;

public record UpdateQuotationRequestDto(
        int quotationId,
        List<UpdateQuotedItemRequestDto> quotedItems,
        BigDecimal totalTax,
        BigDecimal subtotalTax,
        BigDecimal netSubtotal,
        BigDecimal discount,
        BigDecimal transportCost,
        String note,
        QuotationStatus status
) {
}
