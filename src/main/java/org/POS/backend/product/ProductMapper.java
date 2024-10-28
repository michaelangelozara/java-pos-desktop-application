package org.POS.backend.product;

import org.POS.backend.brand.Brand;
import org.POS.backend.brand.BrandMapper;

import java.math.BigDecimal;
import java.util.List;

public class ProductMapper {

    private BrandMapper brandMapper;

    public ProductMapper(){
        this.brandMapper = new BrandMapper();
    }

    public Product toProduct(AddProductRequestDto dto, Brand brand){
        Product product = new Product();
        product.setName(dto.name());
        product.setModel(dto.model());
        product.setCode(dto.code());
        product.setBrand(brand);
        product.setUnit(dto.unit());
        product.setProductTax(dto.tax());
        product.setTaxType(dto.taxType());
        product.setRegularPrice(dto.regularPrice());
        product.setDiscount(dto.discount());
        product.setNote(dto.note());
        product.setAlertQuantity(dto.alertQuantity());
        product.setStatus(dto.status());
        product.setImage(dto.image());
        return product;
    }

    public Product toUpdatedProduct(UpdateProductRequestDto dto, Brand brand){
        Product product = new Product();
        product.setId(dto.productId());
        product.setName(dto.name());
        product.setModel(dto.model());
        product.setCode(dto.code());
        product.setBrand(brand);
        product.setUnit(dto.unit());
        product.setProductTax(dto.tax());
        product.setTaxType(dto.taxType());
        product.setRegularPrice(dto.regularPrice());
        product.setDiscount(dto.discount());
        product.setNote(dto.note());
        product.setAlertQuantity(dto.alertQuantity());
        product.setStatus(dto.status());
        product.setImage(dto.image());
        return product;
    }

    public ProductResponseDto productResponseDto(Product product){
        return new ProductResponseDto(
                product.getName(),
                product.getModel(),
                product.getCode(),
                this.brandMapper.brandResponseDto(product.getBrand()),
                product.getUnit(),
                product.getProductTax(),
                product.getTaxType(),
                product.getRegularPrice(),
                product.getDiscount(),
                product.getNote(),
                product.getAlertQuantity(),
                product.getStatus(),
                product.getImage()
        );
    }

    public List<ProductResponseDto> productResponseDtoList(List<Product> products){
        return products
                .stream()
                .map(this::productResponseDto)
                .toList();
    }
}
