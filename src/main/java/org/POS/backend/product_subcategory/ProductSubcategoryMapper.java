package org.POS.backend.product_subcategory;

import org.POS.backend.product_category.ProductCategory;
import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;

import java.util.List;

public class ProductSubcategoryMapper {

    private CodeGeneratorService codeGeneratorService;

    public ProductSubcategoryMapper(){
        this.codeGeneratorService = new CodeGeneratorService();
    }

    public ProductSubcategory toSubcategory(AddProductSubcategoryRequestDto dto, ProductCategory productCategory){
        ProductSubcategory productSubcategory = new ProductSubcategory();
        productSubcategory.setProductCategory(productCategory);
        productSubcategory.setName(dto.name());
        productSubcategory.setStatus(dto.status());
        productSubcategory.setNote(dto.note());
        productSubcategory.setCode(dto.code());
        productSubcategory.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.SUBCATEGORY_PREFIX));
        return productSubcategory;
    }

    public ProductSubcategory toUpdatedSubcategory(UpdateProductSubcategoryRequestDto dto, ProductCategory productCategory){
        ProductSubcategory productSubcategory = new ProductSubcategory();
        productSubcategory.setId(dto.subcategoryId());
        productSubcategory.setProductCategory(productCategory);
        productSubcategory.setName(dto.name());
        productSubcategory.setStatus(dto.status());
        productSubcategory.setNote(dto.note());
        productSubcategory.setCode(dto.code());
        return productSubcategory;
    }

    public ProductSubcategoryResponseDto subcategoryResponseDto(ProductSubcategory productSubcategory){
        return new ProductSubcategoryResponseDto(
                productSubcategory.getId(),
                productSubcategory.getName(),
                productSubcategory.getStatus(),
                productSubcategory.getProductCategory().getName(),
                productSubcategory.getCode()

        );
    }

    public List<ProductSubcategoryResponseDto> subcategoryResponseDtoList(List<ProductSubcategory> subcategories){
        return subcategories
                .stream()
                .map(this::subcategoryResponseDto)
                .toList();
    }
}
