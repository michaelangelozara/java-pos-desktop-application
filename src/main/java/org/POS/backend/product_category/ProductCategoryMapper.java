package org.POS.backend.product_category;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;

import java.util.List;

public class ProductCategoryMapper {

    private CodeGeneratorService codeGeneratorService;

    public ProductCategoryMapper(){
        this.codeGeneratorService = new CodeGeneratorService();
    }

    public ProductCategory toProductCategory(AddProductCategoryRequestDto dto){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(dto.name());
        productCategory.setStatus(dto.status());
        productCategory.setNote(dto.note());
        productCategory.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.PRODUCT_PREFIX));
        return productCategory;
    }

    public ProductCategory toUpdatedCategory(ProductCategory productCategory, UpdateProductCategoryRequestDto dto){
        productCategory.setId(dto.productCategoryId());
        productCategory.setName(dto.name());
        productCategory.setStatus(dto.status());
        productCategory.setNote(dto.note());
        return productCategory;
    }

    public ProductCategoryResponseDto categoryResponseDto(ProductCategory productCategory){
        return new ProductCategoryResponseDto(
                productCategory.getId(),
                productCategory.getName(),
                productCategory.getStatus(),
                productCategory.getNote(),
                productCategory.getCode()
        );
    }

    public List<ProductCategoryResponseDto> categoryResponseDtoList(List<ProductCategory> categories){
        return categories
                .stream()
                .map(this::categoryResponseDto)
                .toList();
    }
}
