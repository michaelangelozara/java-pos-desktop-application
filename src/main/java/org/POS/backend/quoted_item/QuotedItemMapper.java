package org.POS.backend.quoted_item;

import java.math.BigDecimal;

public class QuotedItemMapper {

    public QuotedItem toQuotedItem(AddQuotedItemRequestDto dto){
        QuotedItem quotedItem = new QuotedItem();
        quotedItem.setQuantity(dto.quantity());
        quotedItem.setPurchasePrice(dto.purchasePrice());
        quotedItem.setSellingPrice(dto.sellingPrice());
        quotedItem.setTax(dto.taxValue());
        quotedItem.setSubtotal(dto.subtotal());

        return quotedItem;
    }
}
