package org.POS.backend.sale_item;

public class SaleItemMapper {

    public SaleItem toSaleItem(AddSaleItemRequestDto dto){
        SaleItem saleItem = new SaleItem();
        saleItem.setPrice(dto.price());
        saleItem.setQuantity(dto.quantity());
        saleItem.setSubtotal(dto.subtotal());
        return saleItem;
    }
}
