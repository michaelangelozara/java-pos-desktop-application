package org.POS.backend.product;

import org.POS.backend.brand.Brand;
import org.POS.backend.brand.BrandMapper;
import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ProductMapper {

    private BrandMapper brandMapper;

    private CodeGeneratorService codeGeneratorService;

    public ProductMapper() {
        this.brandMapper = new BrandMapper();
        this.codeGeneratorService = new CodeGeneratorService();
    }

    public Product toProduct(AddProductRequestDto dto, Brand brand) {
        Product product = new Product();
        product.setName(dto.name());
        product.setModel(dto.model());
        product.setCode(codeGeneratorService.generateProductCode(GlobalVariable.PRODUCT_PREFIX));
        product.setBrand(brand);
        product.setUnit(dto.unit());
        product.setProductTax(12);
        product.setTaxType(dto.taxType());

        if(dto.taxType().equals(ProductTaxType.EXCLUSIVE)){
            product.setSellingPrice(getSellingPrice(dto.purchasePrice()));
            product.setPurchasePrice(dto.purchasePrice());
        }else{
            product.setSellingPrice(dto.sellingPrice());
            product.setPurchasePrice(getPurchasePrice(dto.sellingPrice()));
        }
        product.setPercentageDiscount(dto.percentageDiscount());
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

    public Product toUpdatedProduct(Product product, UpdateProductRequestDto dto, Brand brand) {
        product.setName(dto.name());
        product.setModel(dto.model());
        product.setBrand(brand);
        product.setUnit(dto.unit());
        product.setProductTax(12);
        product.setTaxType(dto.taxType());
//        product.setSellingPrice(getSellingPrice(dto.purchasePrice(), dto.taxType()));
        product.setPercentageDiscount(dto.discount());
        product.setNote(dto.note());
        product.setAlertQuantity(dto.alertQuantity());
        product.setStatus(dto.status());
        product.setImage(dto.image());
        product.setPurchasePrice(dto.purchasePrice());
        product.setStock(dto.stock());
        return product;
    }

    public ProductResponseDto productResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getModel(),
                product.getCode(),
                this.brandMapper.brandResponseDto(product.getBrand()),
                product.getUnit(),
                product.getProductTax(),
                product.getTaxType(),
                product.getPercentageDiscount(),
                product.getNote(),
                product.getAlertQuantity(),
                product.getStatus(),
                product.getImage(),
                product.getSellingPrice(),
                product.getPurchasePrice(),
                product.getStock()
        );
    }

    public List<ProductResponseDto> productResponseDtoList(List<Product> products) {
        return products
                .stream()
                .map(this::productResponseDto)
                .toList();
    }
}
