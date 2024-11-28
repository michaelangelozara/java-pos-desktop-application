package org.POS.backend.product;

import jakarta.persistence.PersistenceException;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.product_attribute.ProductAttribute;
import org.POS.backend.product_category.ProductCategoryDAO;
import org.POS.backend.stock.Stock;
import org.POS.backend.stock.StockType;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ProductService {

    private ProductDAO productDAO;

    private ProductMapper productMapper;

    private UserDAO userDAO;

    private ProductCategoryDAO productCategoryDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
        this.productMapper = new ProductMapper();
        this.userDAO = new UserDAO();
        this.productCategoryDAO = new ProductCategoryDAO();
    }

    public void add(AddProductRequestDto dto, Set<ProductAttribute> productAttributeSet) {
        try{
            var user = this.userDAO.getUserById(CurrentUser.id);
            if (user == null)
                throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

            var category = this.productCategoryDAO.getValidCategory(dto.categoryId());
            if (category == null)
                throw new RuntimeException(GlobalVariable.CATEGORY_NOT_FOUND);

            var product = this.productMapper.toProduct(dto);
            product.setProductCategory(category);

            Stock stock = new Stock();

            // check the type of product
            if (dto.type().equals(ProductType.SIMPLE)) {
                product.setProductType(ProductType.SIMPLE);
                product.setSellingPrice(dto.sellingPrice());
                product.setPurchasePrice(dto.purchasePrice());

                stock.setStockInOrOut(product.getStock());
                stock.setRecentQuantity(product.getStock());
                stock.setPrice(product.getSellingPrice());
            } else {
                product.setProductType(ProductType.VARIABLE);
                product.setSellingPrice(BigDecimal.ZERO);
                product.setPurchasePrice(BigDecimal.ZERO);
                int totalVariationQuantity = 0;
                for (var productAttribute : productAttributeSet) {

                    // check if there's same attribute name
                    var tempAttributeList = new ArrayList<>(productAttributeSet);
                    for(int i = 0; i < tempAttributeList.size(); i++){
                        String currentAttribute = tempAttributeList.get(i).getName();
                        for(int j = 0; j < tempAttributeList.size(); j++){
                            if(i != j && currentAttribute.equals(tempAttributeList.get(j).getName())){
                                throw new RuntimeException("Duplicate Attribute is not Allowed");
                            }
                        }
                    }

                    product.addProductAttribute(productAttribute);

                    for (var variation : productAttribute.getProductVariations()) {

                        var tempVariationList = new ArrayList<>(productAttribute.getProductVariations());
                        for(int i = 0; i < tempVariationList.size(); i++){
                            String currentVariation = tempVariationList.get(i).getVariation();
                            for(int j = 0; j < tempVariationList.size(); j++){
                                if(i != j && currentVariation.equals(tempVariationList.get(j).getVariation())){
                                    throw new RuntimeException("Duplicate Variation is not Allowed");
                                }
                            }
                        }

                        totalVariationQuantity += variation.getQuantity();
                    }
                }
                stock.setStockInOrOut(totalVariationQuantity);
                stock.setRecentQuantity(totalVariationQuantity);
            }

            stock.setDate(LocalDate.now());
            stock.setType(StockType.IN);
            stock.setCode(product.getProductCode());
            user.addStock(stock);
            product.addStock(stock);

            UserLog userLog = new UserLog();
            userLog.setCode(product.getProductCode());
            userLog.setDate(LocalDate.now());
            userLog.setAction(UserActionPrefixes.PRODUCTS_ADD_ACTION_LOG_PREFIX);
            userLog.setUser(user);

            this.productDAO.add(product, userLog);
        }catch (PersistenceException e){
            throw new RuntimeException("Duplicate Product Name is not Allowed");
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void update(UpdateProductRequestDto dto, Set<ProductAttribute> productAttributeSet) {
        var user = this.userDAO.getUserById(CurrentUser.id);
        if (user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);


        var product = this.productDAO.getValidProductById(dto.productId());
        if (product == null)
            throw new RuntimeException("Invalid Product");

        var category = this.productCategoryDAO.getValidCategory(dto.categoryId());
        if (category == null)
            throw new RuntimeException("Invalid Category");

        var updatedProduct = this.productMapper.toUpdatedProduct(product, dto);
        updatedProduct.setProductCategory(category);
        if (updatedProduct.getProductType().equals(ProductType.SIMPLE)) {
            updatedProduct.setSellingPrice(dto.sellingPrice());
            updatedProduct.setPurchasePrice(dto.purchasePrice());
        } else {
            updatedProduct.getProductAttributes().clear();
            for (var newProductAttribute : productAttributeSet) {
                updatedProduct.addProductAttribute(newProductAttribute);
            }
        }

        UserLog userLog = new UserLog();
        userLog.setCode(updatedProduct.getProductCode());
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.PRODUCTS_EDIT_ACTION_LOG_PREFIX);
        userLog.setUser(user);

        this.productDAO.update(updatedProduct, userLog);
    }

    public void delete(int productId) {
        var user = this.userDAO.getUserById(CurrentUser.id);
        if (user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        var product = this.productDAO.getValidProductById(productId);
        if (product == null)
            throw new RuntimeException(GlobalVariable.PRODUCT_NOT_FOUND);

        product.setDeletedAt(LocalDate.now());
        product.setDeleted(true);

        UserLog userLog = new UserLog();
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.PRODUCTS_REMOVE_ACTION_LOG_PREFIX);
        userLog.setCode(product.getProductCode());
        user.addUserLog(userLog);

        this.productDAO.delete(product, userLog);
    }

    public ProductResponseDto getValidProductById(int productId) {
        var product = this.productDAO.getValidProductById(productId);
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

    public List<Product> getAllProductsWithStockByCategoryId(int categoryId) {
        return this.productDAO.getAllValidProductsWithStocksByProductSubcategoryId(categoryId);
    }

    public List<ProductResponseDto> getAllValidProductByName(String name) {
        return this.productMapper.productResponseDtoList(this.productDAO.getAllValidProductByName(name));
    }

    public List<Product> getAllValidProductsByRangeAndCategoryId(LocalDate start, LocalDate end, int categoryId) {
        return this.productDAO.getALlValidProductsByRangeAndCategoryId(start, end, categoryId);
    }

    public List<Product> getAllValidProductsWithoutLimit() {
        return this.productDAO.getAllValidProductWithoutLimit();
    }

    public List<ProductResponseDto> getALlValidProductsWithoutLimitDtoResponse() {
        return this.productMapper.productResponseDtoList(this.productDAO.getAllValidProductWithoutLimit());
    }

    public List<ProductResponseDto> getAllValidProducts() {
        return this.productMapper.productResponseDtoList(this.productDAO.getAllValidProducts());
    }

    public List<ProductResponseDto> getAllValidProductByCategoryId(int id) {
        return this.productMapper.productResponseDtoList(this.productDAO.getAllValidProductByCategoryId(id));
    }

    public List<Product> getAllValidProductByCategoryIdWithoutDto(int id, ProductType type) {
        return this.productDAO.getAllValidProductByCategoryId(id, type);
    }

    public Product getValidProductByIdWithoutDto(int id){
        return this.productDAO.getValidProductById(id);
    }
}
