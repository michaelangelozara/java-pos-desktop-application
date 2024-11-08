package org.POS.backend.purchased_product;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.product.Product;
import org.POS.backend.product.ProductMapper;
import org.POS.backend.purchase.Purchase;

import java.util.List;

public class PurchaseProductMapper {


    private ProductMapper productMapper;

    public PurchaseProductMapper(){
        this.productMapper = new ProductMapper();
    }

    public PurchaseProduct toPurchaseProduct(AddPurchaseProductRequestDto dto, Product product, Purchase purchase){
        PurchaseProduct purchaseProduct = new PurchaseProduct();
        purchaseProduct.setProduct(product);
        purchaseProduct.setPurchase(purchase);
        purchaseProduct.setQuantity(dto.quantity());
        purchaseProduct.setPurchasePrice(dto.purchasePrice());
        purchaseProduct.setSellingPrice(dto.sellingPrice());
        purchaseProduct.setTax(dto.taxValue());
        purchaseProduct.setSubtotal(dto.subtotal());
        return purchaseProduct;
    }

    public PurchaseProductResponseDto toPurchaseResponseDto(PurchaseProduct purchaseProduct){
        return new PurchaseProductResponseDto(
                purchaseProduct.getId(),
                purchaseProduct.getQuantity(),
                purchaseProduct.getPurchasePrice(),
                purchaseProduct.getSellingPrice(),
                purchaseProduct.getTax(),
                purchaseProduct.getSubtotal(),
                this.productMapper.productResponseDto(purchaseProduct.getProduct())
        );
    }

    public List<PurchaseProductResponseDto> toPurchaseResponseDtoList(List<PurchaseProduct> purchaseProducts){
        return purchaseProducts
                .stream()
                .map(this::toPurchaseResponseDto)
                .toList();
    }
}
