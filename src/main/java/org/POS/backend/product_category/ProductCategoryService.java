package org.POS.backend.product_category;

import java.util.List;

public class ProductCategoryService {

    private ProductCategoryDAO productCategoryDAO;

    private ProductCategoryMapper productCategoryMapper;

    public ProductCategoryService(){
        this.productCategoryDAO = new ProductCategoryDAO();
        this.productCategoryMapper = new ProductCategoryMapper();
    }

    public void add(AddProductCategoryRequestDto dto){
        var category = this.productCategoryMapper.toCategory(dto);
        this.productCategoryDAO.add(category);
    }

    public void update(UpdateProductCategoryRequestDto dto){
        var updatedCategory = this.productCategoryMapper.toUpdatedCategory(dto);
        this.productCategoryDAO.update(updatedCategory);
    }

    public void delete(int categoryId){
        this.productCategoryDAO.delete(categoryId);
    }

    public List<ProductCategoryResponseDto> getAllValidCategories(){
        return this.productCategoryMapper.categoryResponseDtoList(this.productCategoryDAO.getAllValidCategories());
    }

    public ProductCategory getValidCategory(int categoryId){
        return this.productCategoryDAO.getValidCategory(categoryId);
    }
}
