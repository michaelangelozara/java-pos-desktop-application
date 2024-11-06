package org.POS.backend.product;

import org.POS.backend.brand.Brand;
import org.POS.backend.brand.BrandMapper;
import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;

import java.math.BigDecimal;
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
        product.setProductTax(dto.productTax());
        product.setTaxType(dto.taxType());
        product.setPurchasePrice(dto.purchasePrice());
        product.setRegularPrice(dto.regularPrice());
        product.setSellingPrice(getSellingPrice(dto.purchasePrice()));
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
            BigDecimal purchasePrice,
            ProductTaxType taxType
    ) {
        if(taxType.equals(ProductTaxType.EXCLUSIVE)){
            return purchasePrice.multiply(BigDecimal.valueOf(0.12));
        }else{
            return getSellingPrice(purchasePrice).divide(BigDecimal.valueOf(1.12)).multiply(BigDecimal.valueOf(0.12));
        }
    }

    public Product toUpdatedProduct(Product product, UpdateProductRequestDto dto, Brand brand) {
        product.setName(dto.name());
        product.setModel(dto.model());
        product.setBrand(brand);
        product.setUnit(dto.unit());
        product.setProductTax(dto.tax());
        product.setTaxType(dto.taxType());
        product.setRegularPrice(dto.regularPrice());
        product.setPercentageDiscount(dto.discount());
        product.setNote(dto.note());
        product.setAlertQuantity(dto.alertQuantity());
        product.setStatus(dto.status());
        product.setImage(dto.image());
        product.setPercentageDiscount(dto.discount());
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
                product.getRegularPrice(),
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
