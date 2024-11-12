package org.POS.backend.brand;

import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.product_subcategory.ProductSubcategoryDAO;

import java.util.List;

public class BrandService {

    private BrandDAO brandDAO;

    private BrandMapper brandMapper;

    private ProductSubcategoryDAO productSubcategoryDAO;

    public BrandService() {
        this.brandDAO = new BrandDAO();
        this.brandMapper = new BrandMapper();
        this.productSubcategoryDAO = new ProductSubcategoryDAO();
    }

    public String add(AddBrandRequestDto dto){
        var subcategory = this.productSubcategoryDAO.getValidSubcategoryById(dto.subcategoryId());

        if(subcategory == null)
            return GlobalVariable.SUBCATEGORY_NOT_FOUND;

        var brand = this.brandMapper.toBrand(dto, subcategory);
        this.brandDAO.add(brand);
        return GlobalVariable.BRAND_ADDED;
    }

    public String update(UpdateBrandRequestDto dto){
        var subcategory = this.productSubcategoryDAO.getValidSubcategoryById(dto.subcategoryId());
        var brand = this.brandDAO.getValidBrandById(dto.brandId());
        if(subcategory == null)
            return GlobalVariable.SUBCATEGORY_NOT_FOUND;

        if(brand == null)
            return GlobalVariable.BRAND_NOT_FOUND;

        var updatedBrand = this.brandMapper.toUpdatedBrand(brand, dto, subcategory);
        this.brandDAO.update(updatedBrand);
        return GlobalVariable.BRAND_UPDATED;
    }

    public String delete(int brandId){
        boolean result = this.brandDAO.delete(brandId);
        if(result)
            return GlobalVariable.BRAND_DELETED;

        return GlobalVariable.BRAND_NOT_FOUND;
    }

    public List<BrandResponseDto> getAllValidBrands(){
        var brands = this.brandDAO.getAllValidBrands();
        return this.brandMapper.brandResponseDtoList(brands);
    }

    public List<BrandResponseDto> getAllBrandByProductSubcategoryId(int subcategoryId){
        var brands = this.brandDAO.getAllValidBrandsByProductSubcategoryId(subcategoryId);
        return this.brandMapper.brandResponseDtoList(brands);
    }

    public BrandResponseDto getValidBrandById(int brandId){
        var brand = this.brandDAO.getValidBrandById(brandId);
        if(brand == null)
            return null;
        return this.brandMapper.brandResponseDto(brand);
    }

    public List<BrandResponseDto> getAllValidBrandByName(String name){
        return this.brandMapper.brandResponseDtoList(this.brandDAO.getAllBrandByName(name));
    }
}
