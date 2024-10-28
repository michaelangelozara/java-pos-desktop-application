package org.POS.backend.product;

import org.POS.backend.brand.BrandDAO;
import org.POS.backend.global_variable.GlobalVariable;

import java.util.List;

public class ProductService {

    private ProductDAO productDAO;

    private ProductMapper productMapper;

    private BrandDAO brandDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
        this.productMapper = new ProductMapper();
        this.brandDAO = new BrandDAO();
    }

    public String add(AddProductRequestDto dto){
        var brand = this.brandDAO.getValidBrand(dto.brandId());
        if(brand == null)
            return GlobalVariable.BRAND_NOT_FOUND;

        var product = this.productMapper.toProduct(dto, brand);
        this.productDAO.add(product);
        return GlobalVariable.PRODUCT_ADDED;
    }

    public String update(UpdateProductRequestDto dto){
        var brand = this.brandDAO.getValidBrand(dto.brandId());
        if(brand == null)
            return GlobalVariable.BRAND_NOT_FOUND;
        var product = this.productMapper.toUpdatedProduct(dto, brand);
        this.productDAO.update(product);
        return GlobalVariable.PRODUCT_UPDATED;
    }

    public String delete(int productId){
        boolean result = this.productDAO.delete(productId);
        if(result)
            return GlobalVariable.PRODUCT_DELETED;

        return GlobalVariable.PRODUCT_NOT_FOUND;
    }

    public ProductResponseDto getValidProductById(int productId){
        var product = this.productDAO.getValidProduct(productId);
        if(product == null)
            return null;

        return this.productMapper.productResponseDto(product);
    }

    public List<ProductResponseDto> getAllValidProducts(){
        var products = this.productDAO.getAllValidProducts();
        return this.productMapper.productResponseDtoList(products);
    }
}
