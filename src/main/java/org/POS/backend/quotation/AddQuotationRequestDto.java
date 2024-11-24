package org.POS.backend.quotation;

import org.POS.backend.quoted_item.AddQuotedItemRequestDto;

import java.util.List;

public record AddQuotationRequestDto(
        int clientId,
        String note,
        List<AddQuotedItemRequestDto> quotedItemDtoList
) {
}
