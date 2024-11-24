package org.POS.backend.product;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class ProductMapper {

    private CodeGeneratorService codeGeneratorService;

    public ProductMapper() {
        this.codeGeneratorService = new CodeGeneratorService();
    }

    public Product toProduct(AddProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setProductCode(codeGeneratorService.generateProductCode(GlobalVariable.PRODUCT_PREFIX));
        product.setUnit(dto.unit());
        product.setDate(LocalDate.now());
        product.setNote(dto.note());
        product.setAlertQuantity(dto.alertQuantity());
        product.setStatus(dto.status());
        product.setImage(dto.image());
        product.setStock(dto.stock());
        return product;
    }

    public BigDecimal getSellingPrice(
            BigDecimal purchasePrice
    ) {
        return purchasePrice.multiply(BigDecimal.valueOf(1.12));
    }

    public BigDecimal getPurchasePrice(
            BigDecimal sellingPrice
    ) {
        return sellingPrice.multiply(BigDecimal.valueOf(0.12)).divide(BigDecimal.valueOf(1.12), RoundingMode.HALF_UP);
    }

    public Product toUpdatedProduct(Product product, UpdateProductRequestDto dto) {
        product.setName(dto.name());
        product.setUnit(dto.unit());
        product.setDate(LocalDate.now());
        product.setNote(dto.note());
        product.setAlertQuantity(dto.alertQuantity());
        product.setStatus(dto.status());
        product.setImage(dto.image().isEmpty() ? product.getImage() : dto.image());
        product.setStock(dto.stock());
        return product;
    }

    public ProductResponseDto productResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getProductCode(),
                product.getUnit(),
                product.getNote(),
                product.getAlertQuantity(),
                product.getStatus(),
                product.getImage(),
                product.getSellingPrice(),
                product.getPurchasePrice(),
                product.getStock(),
                product.getDate(),
                product.getProductCategory(),
                product.getProductAttributes(),
                product.getProductType()
        );
    }

    public List<ProductResponseDto> productResponseDtoList(List<Product> products) {
        return products
                .stream()
                .map(this::productResponseDto)
                .toList();
    }
}
