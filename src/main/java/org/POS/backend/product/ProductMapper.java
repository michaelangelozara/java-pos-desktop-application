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
        product.setSellingPrice(getSellingPrice(dto.regularPrice(), dto.productTax(), dto.percentageDiscount(), dto.taxType()));
        product.setPercentageDiscount(dto.percentageDiscount());
        product.setNote(dto.note());
        product.setAlertQuantity(dto.alertQuantity());
        product.setStatus(dto.status());
        product.setImage(dto.image());
        product.setStock(dto.stock());
        return product;
    }

    private BigDecimal getSellingPrice(
            BigDecimal regularPrice,
            int productTax,
            int percentageDiscount,
            ProductTaxType taxType
    ) {
        BigDecimal discountPrice = regularPrice.multiply(BigDecimal.valueOf(percentageDiscount / 100.0));
        BigDecimal discountedPrice = regularPrice.subtract(discountPrice);
        BigDecimal vatTax = discountedPrice.multiply(BigDecimal.valueOf(productTax / 100.0));

        if (taxType.equals(ProductTaxType.INCLUSIVE)) {
            // If inclusive, VAT is already included, so we return the discounted price directly
            return discountedPrice;
        } else {
            // If exclusive, VAT is added to the discounted price
            return discountedPrice.add(vatTax);
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
