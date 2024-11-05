package org.POS.backend.brand;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.product_subcategory.ProductSubcategory;
import org.POS.backend.product_subcategory.ProductSubcategoryMapper;

import java.util.List;

public class BrandMapper {

    private CodeGeneratorService codeGeneratorService;

    private ProductSubcategoryMapper productSubcategoryMapper;

    public BrandMapper(){
        this.codeGeneratorService = new CodeGeneratorService();
        this.productSubcategoryMapper = new ProductSubcategoryMapper();
    }

    public Brand toBrand(AddBrandRequestDto dto, ProductSubcategory productSubcategory){
        Brand brand = new Brand();
        brand.setName(dto.name());
        brand.setStatus(dto.status());
        brand.setProductSubcategory(productSubcategory);
        brand.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.BRAND_PREFIX));
        return brand;
    }

    public Brand toUpdatedBrand(UpdateBrandRequestDto dto, ProductSubcategory productSubcategory){
        Brand brand = new Brand();
        brand.setId(dto.brandId());
        brand.setName(dto.name());
        brand.setStatus(dto.status());
        brand.setProductSubcategory(productSubcategory);
        return brand;
    }

    public BrandResponseDto brandResponseDto(Brand brand){
        return new BrandResponseDto(
                brand.getId(),
                brand.getName(),
                brand.getStatus(),
                this.productSubcategoryMapper.subcategoryResponseDto(brand.getProductSubcategory())
        );
    }

    public List<BrandResponseDto> brandResponseDtoList(List<Brand> brands){
        return brands
                .stream()
                .map(this::brandResponseDto)
                .toList();
    }
}
