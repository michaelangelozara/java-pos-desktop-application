package org.POS.backend.category;

import java.util.List;

public class CategoryService {

    private CategoryDAO categoryDAO;

    private CategoryMapper categoryMapper;

    public CategoryService(){
        this.categoryDAO = new CategoryDAO();
        this.categoryMapper = new CategoryMapper();
    }

    public void add(AddCategoryRequestDto dto){
        var category = this.categoryMapper.toCategory(dto);
        this.categoryDAO.add(category);
    }

    public void update(UpdateCategoryRequestDto dto){
        var updatedCategory = this.categoryMapper.toUpdatedCategory(dto);
        this.categoryDAO.update(updatedCategory);
    }

    public void delete(int categoryId){
        this.categoryDAO.delete(categoryId);
    }

    public List<CategoryResponseDto> getAllValidCategories(){
        return this.categoryMapper.categoryResponseDtoList(this.categoryDAO.getAllValidCategories());
    }

    public CategoryResponseDto getValidCategory(int categoryId){
        var category = this.categoryDAO.getValidCategory(categoryId);
        if(category != null)
            return this.categoryMapper.categoryResponseDto(category);
        return null;
    }
}
