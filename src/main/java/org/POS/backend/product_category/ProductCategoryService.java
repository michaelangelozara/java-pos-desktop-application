package org.POS.backend.product_category;

import java.util.List;

public class ProductCategoryService {

    private ProductCategoryDAO productCategoryDAO;

    private ProductCategoryMapper productCategoryMapper;

    public ProductCategoryService() {
        this.productCategoryDAO = new ProductCategoryDAO();
        this.productCategoryMapper = new ProductCategoryMapper();
    }

    public void add(AddProductCategoryRequestDto dto) {
        var category = this.productCategoryMapper.toProductCategory(dto);
        this.productCategoryDAO.add(category);
    }

    public void update(UpdateProductCategoryRequestDto dto) {
        var productCategory = this.productCategoryDAO.getValidCategory(dto.productCategoryId());
        if (productCategory != null) {
            var updatedCategory = this.productCategoryMapper.toUpdatedCategory(productCategory, dto);
            this.productCategoryDAO.update(updatedCategory);
        }
    }

    public void delete(int categoryId) {
        this.productCategoryDAO.delete(categoryId);
    }

    public List<ProductCategoryResponseDto> getAllValidProductCategories() {
        return
                this.productCategoryMapper
                        .categoryResponseDtoList(this.productCategoryDAO.getAllValidCategories());
    }

}
