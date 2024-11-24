package org.POS.backend.quoted_item;

public record AddQuotedItemRequestDto(
        Integer productId,
        Integer variationId,
        int quantity,
        QuotedItemType type
) {
}
