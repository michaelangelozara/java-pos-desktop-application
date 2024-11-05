package org.POS.backend.product_subcategory;

import org.POS.backend.product_category.ProductCategory;
import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.product_category.ProductCategoryMapper;

import java.util.List;

public class ProductSubcategoryMapper {

    private CodeGeneratorService codeGeneratorService;

    private ProductCategoryMapper productCategoryMapper;

    public ProductSubcategoryMapper(){
        this.codeGeneratorService = new CodeGeneratorService();
        this.productCategoryMapper = new ProductCategoryMapper();
    }

    public ProductSubcategory toProductSubcategory(AddProductSubcategoryRequestDto dto, ProductCategory productCategory){
        ProductSubcategory productSubcategory = new ProductSubcategory();
        productSubcategory.setProductCategory(productCategory);
        productSubcategory.setName(dto.name());
        productSubcategory.setStatus(dto.status());
        productSubcategory.setNote(dto.note());
        productSubcategory.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.PRODUCT_SUBCATEGORY_PREFIX));
        return productSubcategory;
    }

    public ProductSubcategory toUpdatedProductSubcategory(ProductSubcategory productSubcategory, UpdateProductSubcategoryRequestDto dto, ProductCategory productCategory){
        productSubcategory.setProductCategory(productCategory);
        productSubcategory.setName(dto.name());
        productSubcategory.setStatus(dto.status());
        productSubcategory.setNote(dto.note());
        return productSubcategory;
    }

    public ProductSubcategoryResponseDto subcategoryResponseDto(ProductSubcategory productSubcategory){
        return new ProductSubcategoryResponseDto(
                productSubcategory.getId(),
                productSubcategory.getName(),
                productSubcategory.getStatus(),
                productSubcategory.getProductCategory().getName(),
                productSubcategory.getCode(),
                this.productCategoryMapper.categoryResponseDto(productSubcategory.getProductCategory()),
                productSubcategory.getNote()

        );
    }

    public List<ProductSubcategoryResponseDto> subcategoryResponseDtoList(List<ProductSubcategory> subcategories){
        return subcategories
                .stream()
                .map(this::subcategoryResponseDto)
                .toList();
    }
}
