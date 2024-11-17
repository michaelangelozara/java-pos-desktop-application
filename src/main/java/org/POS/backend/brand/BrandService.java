package org.POS.backend.brand;

import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.product_subcategory.ProductSubcategoryDAO;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;
import org.POS.backend.user_log.UserLogDAO;

import java.time.LocalDate;
import java.util.List;

public class BrandService {

    private BrandDAO brandDAO;

    private BrandMapper brandMapper;

    private ProductSubcategoryDAO productSubcategoryDAO;

    private UserDAO userDAO;

    public BrandService() {
        this.brandDAO = new BrandDAO();
        this.brandMapper = new BrandMapper();
        this.productSubcategoryDAO = new ProductSubcategoryDAO();
        this.userDAO = new UserDAO();
    }

    public String add(AddBrandRequestDto dto) {
        var subcategory = this.productSubcategoryDAO.getValidSubcategoryById(dto.subcategoryId());
        var user = this.userDAO.getUserById(CurrentUser.id);

        if (subcategory == null)
            return GlobalVariable.SUBCATEGORY_NOT_FOUND;

        if (user == null)
            return GlobalVariable.USER_NOT_FOUND;

        var brand = this.brandMapper.toBrand(dto, subcategory);

        UserLog userLog = new UserLog();
        userLog.setCode(brand.getCode());
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.BRAND_ADD_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        this.brandDAO.add(brand, userLog);
        return GlobalVariable.BRAND_ADDED;
    }

    public String update(UpdateBrandRequestDto dto) {
        var subcategory = this.productSubcategoryDAO.getValidSubcategoryById(dto.subcategoryId());
        var brand = this.brandDAO.getValidBrandById(dto.brandId());
        var user = this.userDAO.getUserById(CurrentUser.id);
        if (subcategory == null)
            return GlobalVariable.SUBCATEGORY_NOT_FOUND;

        if (brand == null)
            return GlobalVariable.BRAND_NOT_FOUND;

        if(user == null)
            return GlobalVariable.USER_NOT_FOUND;

        UserLog userLog = new UserLog();
        userLog.setCode(brand.getCode());
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.BRAND_EDIT_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        var updatedBrand = this.brandMapper.toUpdatedBrand(brand, dto, subcategory);
        this.brandDAO.update(updatedBrand, userLog);
        return GlobalVariable.BRAND_UPDATED;
    }

    public void delete(int brandId) {
        var user = this.userDAO.getUserById(CurrentUser.id);

        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        UserLog userLog = new UserLog();
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.BRAND_REMOVE_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        boolean result = this.brandDAO.delete(brandId, userLog);
        if(!result)
            throw new RuntimeException("Failed to remove brand");
    }

    public List<BrandResponseDto> getAllValidBrands() {
        var brands = this.brandDAO.getAllValidBrands();
        return this.brandMapper.brandResponseDtoList(brands);
    }

    public List<BrandResponseDto> getAllBrandByProductSubcategoryId(int subcategoryId) {
        var brands = this.brandDAO.getAllValidBrandsByProductSubcategoryId(subcategoryId);
        return this.brandMapper.brandResponseDtoList(brands);
    }

    public BrandResponseDto getValidBrandById(int brandId) {
        var brand = this.brandDAO.getValidBrandById(brandId);
        if (brand == null)
            return null;
        return this.brandMapper.brandResponseDto(brand);
    }

    public List<BrandResponseDto> getAllValidBrandByName(String name) {
        return this.brandMapper.brandResponseDtoList(this.brandDAO.getAllBrandByName(name));
    }
}
