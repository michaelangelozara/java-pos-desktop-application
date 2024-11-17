package org.POS.backend.product_category;

import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;

import java.time.LocalDate;
import java.util.List;

public class ProductCategoryService {

    private ProductCategoryDAO productCategoryDAO;

    private ProductCategoryMapper productCategoryMapper;

    private UserDAO userDAO;

    public ProductCategoryService() {
        this.productCategoryDAO = new ProductCategoryDAO();
        this.productCategoryMapper = new ProductCategoryMapper();
        this.userDAO = new UserDAO();
    }

    public void add(AddProductCategoryRequestDto dto) {
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        var category = this.productCategoryMapper.toProductCategory(dto);

        UserLog userLog = new UserLog();
        userLog.setCode(category.getCode());
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.PRODUCT_CATEGORIES_ADD_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        this.productCategoryDAO.add(category, userLog);
    }

    public void update(UpdateProductCategoryRequestDto dto) {
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        var productCategory = this.productCategoryDAO.getValidCategory(dto.productCategoryId());
        if (productCategory != null) {
            var updatedCategory = this.productCategoryMapper.toUpdatedCategory(productCategory, dto);

            UserLog userLog = new UserLog();
            userLog.setCode(updatedCategory.getCode());
            userLog.setDate(LocalDate.now());
            userLog.setAction(UserActionPrefixes.PRODUCT_CATEGORIES_EDIT_ACTION_LOG_PREFIX);
            user.addUserLog(userLog);

            this.productCategoryDAO.update(updatedCategory, userLog);
        }
    }

    public void delete(int categoryId) {
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        UserLog userLog = new UserLog();
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.PRODUCT_CATEGORIES_REMOVE_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        this.productCategoryDAO.delete(categoryId, userLog);
    }

    public List<ProductCategoryResponseDto> getAllValidProductCategories() {
        return
                this.productCategoryMapper
                        .categoryResponseDtoList(this.productCategoryDAO.getAllValidCategories());
    }

    public List<ProductCategoryResponseDto> getAllValidProductCategoryByName(String name){
        return this.productCategoryMapper.categoryResponseDtoList(this.productCategoryDAO.getAllValidProductCategoryByName(name));
    }

}
