package org.POS.backend.purchased_item;

import org.POS.backend.product.ProductMapper;

import java.util.List;

public class PurchaseItemMapper {


    private ProductMapper productMapper;

    public PurchaseItemMapper() {
        this.productMapper = new ProductMapper();
    }

    public PurchaseItem toPurchaseItem() {

        return null;
    }

    public PurchaseItemResponseDto toPurchaseItemResponseDto(PurchaseItem purchaseItem) {
        return null;
    }

    public List<PurchaseItemResponseDto> toPurchaseItemResponseDtoList(List<PurchaseItem> purchaseItems) {
        return purchaseItems
                .stream()
                .map(this::toPurchaseItemResponseDto)
                .toList();
    }
}
