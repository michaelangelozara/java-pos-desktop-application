package org.POS.backend.category;

import java.util.List;

public class CategoryMapper {

    public Category toCategory(AddCategoryRequestDto dto){
        Category category = new Category();
        category.setName(dto.name());
        category.setStatus(dto.status());
        category.setNote(dto.note());
        return category;
    }

    public Category toUpdatedCategory(UpdateCategoryRequestDto dto){
        Category category = new Category();
        category.setId(dto.categoryId());
        category.setName(dto.name());
        category.setStatus(dto.status());
        category.setNote(dto.note());
        return category;
    }

    public CategoryResponseDto categoryResponseDto(Category category){
        return new CategoryResponseDto(
                category.getName(),
                category.getStatus(),
                category.getNote()
        );
    }

    public List<CategoryResponseDto> categoryResponseDtoList(List<Category> categories){
        return categories
                .stream()
                .map(this::categoryResponseDto)
                .toList();
    }
}
