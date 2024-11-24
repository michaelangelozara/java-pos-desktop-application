package org.POS.backend.return_product;

public record AddReturnItemRequestDto(
        int productId,
        int returnedQuantity
) {
}
