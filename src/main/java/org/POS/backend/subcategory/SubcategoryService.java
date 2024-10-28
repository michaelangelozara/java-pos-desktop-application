package org.POS.backend.subcategory;

import org.POS.backend.category.CategoryDAO;
import org.POS.backend.global_variable.GlobalVariable;

import java.util.List;

public class SubcategoryService {

    private SubcategoryDAO subcategoryDAO;

    private SubcategoryMapper subcategoryMapper;

    private CategoryDAO categoryDAO;

    public SubcategoryService(){
        this.subcategoryDAO = new SubcategoryDAO();
        this.subcategoryMapper = new SubcategoryMapper();
        this.categoryDAO = new CategoryDAO();
    }

    public String add(AddSubcategoryRequestDto dto){
        var category = this.categoryDAO.getValidCategory(dto.categoryId());

        if(category == null)
            return GlobalVariable.SUBCATEGORY_NOT_FOUND;

        var subcategory = this.subcategoryMapper.toSubcategory(dto, category);
        this.subcategoryDAO.add(subcategory);
        return GlobalVariable.SUBCATEGORY_ADDED;
    }

    public String update(UpdateSubcategoryRequestDto dto){
        var category = this.categoryDAO.getValidCategory(dto.categoryId());
        if(category == null)
            return GlobalVariable.SUBCATEGORY_NOT_FOUND;
        var subcategory = this.subcategoryMapper.toUpdatedSubcategory(dto, category);
        this.subcategoryDAO.update(subcategory);
        return GlobalVariable.SUBCATEGORY_UPDATED;
    }

    public String delete(int subcategoryId){
        boolean result = this.subcategoryDAO.delete(subcategoryId);
        if(result)
            return GlobalVariable.SUBCATEGORY_DELETED;
        return GlobalVariable.SUBCATEGORY_NOT_FOUND;
    }

    public SubcategoryResponseDto getValidSubcategory(int subcategoryId){
        var subcategory = this.subcategoryDAO.getValidSubcategoryById(subcategoryId);

        if(subcategory != null)
            return this.subcategoryMapper.subcategoryResponseDto(subcategory);
        return null;
    }

    public List<SubcategoryResponseDto> getAllValidSubcategories(){
        var subcategories = this.subcategoryDAO.getAllValidSubcategories();
        return this.subcategoryMapper.subcategoryResponseDtoList(subcategories);
    }
}
