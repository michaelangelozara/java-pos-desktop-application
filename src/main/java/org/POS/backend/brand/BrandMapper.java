package org.POS.backend.brand;

import org.POS.backend.subcategory.Subcategory;

import java.util.List;

public class BrandMapper {

    public Brand toBrand(AddBrandRequestDto dto, Subcategory subcategory){
        Brand brand = new Brand();
        brand.setName(dto.name());
        brand.setStatus(dto.status());
        brand.setSubcategory(subcategory);
        return brand;
    }

    public Brand toUpdatedBrand(UpdateBrandRequestDto dto, Subcategory subcategory){
        Brand brand = new Brand();
        brand.setId(dto.brandId());
        brand.setName(dto.name());
        brand.setStatus(dto.status());
        brand.setSubcategory(subcategory);
        return brand;
    }

    public BrandResponseDto brandResponseDto(Brand brand){
        return new BrandResponseDto(
                brand.getName(),
                brand.getStatus()
        );
    }

    public List<BrandResponseDto> brandResponseDtoList(List<Brand> brands){
        return brands
                .stream()
                .map(this::brandResponseDto)
                .toList();
    }
}
