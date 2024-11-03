package org.POS.backend.product_subcategory;

import org.POS.backend.product_category.ProductCategoryDAO;
import org.POS.backend.global_variable.GlobalVariable;

import java.util.List;

public class ProductSubcategoryService {

    private ProductSubcategoryDAO productSubcategoryDAO;

    private ProductSubcategoryMapper productSubcategoryMapper;

    private ProductCategoryDAO productCategoryDAO;

    public ProductSubcategoryService(){
        this.productSubcategoryDAO = new ProductSubcategoryDAO();
        this.productSubcategoryMapper = new ProductSubcategoryMapper();
        this.productCategoryDAO = new ProductCategoryDAO();
    }

    public String add(AddProductSubcategoryRequestDto dto){
        var category = this.productCategoryDAO.getValidCategory(dto.categoryId());

        if(category == null)
            return GlobalVariable.SUBCATEGORY_NOT_FOUND;

        var subcategory = this.productSubcategoryMapper.toSubcategory(dto, category);
        this.productSubcategoryDAO.add(subcategory);
        return GlobalVariable.SUBCATEGORY_ADDED;
    }

    public String update(UpdateProductSubcategoryRequestDto dto){
        var category = this.productCategoryDAO.getValidCategory(dto.categoryId());
        if(category == null)
            return GlobalVariable.SUBCATEGORY_NOT_FOUND;
        var subcategory = this.productSubcategoryMapper.toUpdatedSubcategory(dto, category);
        this.productSubcategoryDAO.update(subcategory);
        return GlobalVariable.SUBCATEGORY_UPDATED;
    }

    public String delete(int subcategoryId){
        boolean result = this.productSubcategoryDAO.delete(subcategoryId);
        if(result)
            return GlobalVariable.SUBCATEGORY_DELETED;
        return GlobalVariable.SUBCATEGORY_NOT_FOUND;
    }

    public ProductSubcategoryResponseDto getValidSubcategory(int subcategoryId){
        var subcategory = this.productSubcategoryDAO.getValidSubcategoryById(subcategoryId);

        if(subcategory != null)
            return this.productSubcategoryMapper.subcategoryResponseDto(subcategory);
        return null;
    }

    public List<ProductSubcategoryResponseDto> getAllValidSubcategories(){
        var subcategories = this.productSubcategoryDAO.getAllValidSubcategories();
        return this.productSubcategoryMapper.subcategoryResponseDtoList(subcategories);
    }

    public List<ProductSubcategoryResponseDto> getAllValidSubcategoriesByCategoryId(int categoryId){
        return this.productSubcategoryMapper.subcategoryResponseDtoList(this.productSubcategoryDAO.getAllValidSubcategoriesByCategoryId(categoryId));
    }
}
