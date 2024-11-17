package org.POS.backend.product_subcategory;

import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.product_category.ProductCategoryDAO;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;

import java.time.LocalDate;
import java.util.List;

public class ProductSubcategoryService {

    private ProductSubcategoryDAO productSubcategoryDAO;

    private ProductSubcategoryMapper productSubcategoryMapper;

    private ProductCategoryDAO productCategoryDAO;

    private UserDAO userDAO;

    public ProductSubcategoryService(){
        this.productSubcategoryDAO = new ProductSubcategoryDAO();
        this.productSubcategoryMapper = new ProductSubcategoryMapper();
        this.productCategoryDAO = new ProductCategoryDAO();
        this.userDAO = new UserDAO();
    }

    public String add(AddProductSubcategoryRequestDto dto){
        var category = this.productCategoryDAO.getValidCategory(dto.categoryId());

        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        if(category == null)
            return GlobalVariable.SUBCATEGORY_NOT_FOUND;

        var subcategory = this.productSubcategoryMapper.toProductSubcategory(dto, category);

        UserLog userLog = new UserLog();
        userLog.setCode(subcategory.getCode());
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.PRODUCT_SUB_CATEGORIES_ADD_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        this.productSubcategoryDAO.add(subcategory, userLog);
        return GlobalVariable.SUBCATEGORY_ADDED;
    }

    public String update(UpdateProductSubcategoryRequestDto dto){
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        var category = this.productCategoryDAO.getValidCategory(dto.categoryId());
        var productSubcategory = this.productSubcategoryDAO.getValidSubcategoryById(dto.subcategoryId());
        if(category == null)
            return GlobalVariable.SUBCATEGORY_NOT_FOUND;

        if(productSubcategory == null)
            return GlobalVariable.SUBCATEGORY_NOT_FOUND;

        var subcategory = this.productSubcategoryMapper.toUpdatedProductSubcategory(productSubcategory, dto, category);

        UserLog userLog = new UserLog();
        userLog.setCode(subcategory.getCode());
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.PRODUCT_SUB_CATEGORIES_EDIT_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        this.productSubcategoryDAO.update(subcategory, userLog);
        return GlobalVariable.SUBCATEGORY_UPDATED;
    }

    public String delete(int subcategoryId){
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        UserLog userLog = new UserLog();
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.PRODUCT_SUB_CATEGORIES_REMOVE_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        boolean result = this.productSubcategoryDAO.delete(subcategoryId, userLog);
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

    public List<ProductSubcategoryResponseDto> getAllValidProductSubcategoryByName(String name){
        return this.productSubcategoryMapper.subcategoryResponseDtoList(this.productSubcategoryDAO.getAllValidSubcategoryByName(name));
    }
}
