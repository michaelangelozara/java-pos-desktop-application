package org.POS.backend.purchased_item;

import org.POS.backend.product.ProductMapper;

import java.util.List;

public class PurchaseItemMapper {


    private ProductMapper productMapper;

    public PurchaseItemMapper(){
        this.productMapper = new ProductMapper();
    }

    public PurchaseItem toPurchaseItem(AddPurchaseItemRequestDto dto){
        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setQuantity(dto.quantity());
        purchaseItem.setPurchasePrice(dto.purchasePrice());
        purchaseItem.setSellingPrice(dto.sellingPrice());
        purchaseItem.setTax(dto.taxValue());
        purchaseItem.setSubtotal(dto.subtotal());
        return purchaseItem;
    }

    public PurchaseItemResponseDto toPurchaseItemResponseDto(PurchaseItem purchaseItem){
        return new PurchaseItemResponseDto(
                purchaseItem.getId(),
                purchaseItem.getQuantity(),
                purchaseItem.getPurchasePrice(),
                purchaseItem.getSellingPrice(),
                purchaseItem.getTax(),
                purchaseItem.getSubtotal(),
                this.productMapper.productResponseDto(purchaseItem.getProduct())
        );
    }

    public List<PurchaseItemResponseDto> toPurchaseItemResponseDtoList(List<PurchaseItem> purchaseItems){
        return purchaseItems
                .stream()
                .map(this::toPurchaseItemResponseDto)
                .toList();
    }
}
