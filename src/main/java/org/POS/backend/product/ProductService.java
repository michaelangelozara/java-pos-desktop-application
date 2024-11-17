package org.POS.backend.product;

import org.POS.backend.brand.BrandDAO;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;

import java.time.LocalDate;
import java.util.List;

public class ProductService {

    private ProductDAO productDAO;

    private ProductMapper productMapper;

    private BrandDAO brandDAO;

    private UserDAO userDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
        this.productMapper = new ProductMapper();
        this.brandDAO = new BrandDAO();
        this.userDAO = new UserDAO();
    }

    public String add(AddProductRequestDto dto) {
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        var brand = this.brandDAO.getValidBrandById(dto.brandId());
        if (brand == null)
            return GlobalVariable.BRAND_NOT_FOUND;

        var product = this.productMapper.toProduct(dto, brand);

        UserLog userLog = new UserLog();
        userLog.setCode(product.getCode());
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.PRODUCTS_ADD_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        this.productDAO.add(product, userLog);
        return GlobalVariable.PRODUCT_ADDED;
    }

    public String update(UpdateProductRequestDto dto) {
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        var brand = this.brandDAO.getValidBrandById(dto.brandId());

        var product = this.productDAO.getValidProduct(dto.productId());
        if (brand == null)
            return GlobalVariable.BRAND_NOT_FOUND;


        var updatedProduct = this.productMapper.toUpdatedProduct(product, dto, brand);

        UserLog userLog = new UserLog();
        userLog.setCode(updatedProduct.getCode());
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.PRODUCTS_EDIT_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        this.productDAO.update(updatedProduct, userLog);
        return GlobalVariable.PRODUCT_UPDATED;
    }

    public String delete(int productId) {
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        UserLog userLog = new UserLog();
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.PRODUCTS_REMOVE_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        boolean result = this.productDAO.delete(productId, userLog);
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
