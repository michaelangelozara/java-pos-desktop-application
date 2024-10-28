package org.POS.backend.subcategory;

import org.POS.backend.category.Category;

import java.util.List;

public class SubcategoryMapper {

    public Subcategory toSubcategory(AddSubcategoryRequestDto dto, Category category){
        Subcategory subcategory = new Subcategory();
        subcategory.setCategory(category);
        subcategory.setName(dto.name());
        subcategory.setStatus(dto.status());
        subcategory.setNote(dto.note());
        return subcategory;
    }

    public Subcategory toUpdatedSubcategory(UpdateSubcategoryRequestDto dto, Category category){
        Subcategory subcategory = new Subcategory();
        subcategory.setId(dto.subcategoryId());
        subcategory.setCategory(category);
        subcategory.setName(dto.name());
        subcategory.setStatus(dto.status());
        subcategory.setNote(dto.note());
        return subcategory;
    }

    public SubcategoryResponseDto subcategoryResponseDto(Subcategory subcategory){
        return new SubcategoryResponseDto(
                subcategory.getId(),
                subcategory.getName(),
                subcategory.getStatus(),
                subcategory.getNote()
        );
    }

    public List<SubcategoryResponseDto> subcategoryResponseDtoList(List<Subcategory> subcategories){
        return subcategories
                .stream()
                .map(this::subcategoryResponseDto)
                .toList();
    }
}
