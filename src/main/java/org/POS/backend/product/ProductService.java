package org.POS.backend.product;

import org.POS.backend.brand.BrandDAO;
import org.POS.backend.global_variable.GlobalVariable;

import java.time.LocalDate;
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

    public String add(AddProductRequestDto dto) {
        var brand = this.brandDAO.getValidBrandById(dto.brandId());
        if (brand == null)
            return GlobalVariable.BRAND_NOT_FOUND;

        var product = this.productMapper.toProduct(dto, brand);
        this.productDAO.add(product);
        return GlobalVariable.PRODUCT_ADDED;
    }

    public String update(UpdateProductRequestDto dto) {
        var brand = this.brandDAO.getValidBrandById(dto.brandId());

        var product = this.productDAO.getValidProduct(dto.productId());
        if (brand == null)
            return GlobalVariable.BRAND_NOT_FOUND;


        var updatedProduct = this.productMapper.toUpdatedProduct(product, dto, brand);
        this.productDAO.update(updatedProduct);
        return GlobalVariable.PRODUCT_UPDATED;
    }

    public String delete(int productId) {
        boolean result = this.productDAO.delete(productId);
        if (result)
            return GlobalVariable.PRODUCT_DELETED;

        return GlobalVariable.PRODUCT_NOT_FOUND;
    }

    public ProductResponseDto getValidProductById(int productId) {
        var product = this.productDAO.getValidProduct(productId);
        if (product == null)
            return null;

        return this.productMapper.productResponseDto(product);
    }

    public List<ProductResponseDto> getAllValidProductsWithLimit() {
        var products = this.productDAO.getAllValidProductsWithLimit();
        return this.productMapper.productResponseDtoList(products);
    }

    public List<ProductResponseDto> getAllValidProductsBelowAlertQuantity() {
        return this.productMapper.productResponseDtoList(this.productDAO.getAllValidProductsBelowAlertQuantity());
    }

    public List<ProductResponseDto> getAllValidProductByProductSubcategoryId(int productSubcategoryId, boolean isGreaterThanZero) {
        return this.productMapper.productResponseDtoList(this.productDAO.getAllValidProductsByProductSubcategoryId(productSubcategoryId, isGreaterThanZero));
    }

    public List<Product> getAllProductWithStockBySubcategoryId(int subcategoryId) {
        return this.productDAO.getAllValidProductsWithStocksByProductSubcategoryId(subcategoryId);
    }

    public List<ProductResponseDto> getAllValidProductByName(String name) {
        return this.productMapper.productResponseDtoList(this.productDAO.getAllValidProductByName(name));
    }

    public List<ProductResponseDto> getAllValidProductByNameAndQuantityGreaterThanZero(String name) {
        return this.productMapper.productResponseDtoList(this.productDAO.getAllValidProductByNameQuantityGreaterThanZero(name));
    }

    public List<Product> getAllValidProductByRangeAndSubcategoryId(LocalDate start, LocalDate end, int subcategoryId) {
        return this.productDAO.getALlValidProductByRangeAndSubcategoryId(start, end, subcategoryId);
    }

    public List<Product> getAllValidProductsByRange(LocalDate start, LocalDate end){
        return this.productDAO.getALlValidProductByRange(start, end);
    }

    public List<Product> getAllValidProductsWithoutLimit(){
        return this.productDAO.getAllValidProductWithoutLimit();
    }

    public List<ProductResponseDto> getALlValidProductsWithoutLimitDtoResponse(){
        return this.productMapper.productResponseDtoList(this.productDAO.getAllValidProductWithoutLimit());
    }

    public List<ProductResponseDto> getAllValidProducts(){
        return this.productMapper.productResponseDtoList(this.productDAO.getAllValidProducts());
    }
}
