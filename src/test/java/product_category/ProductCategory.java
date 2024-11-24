package product_category;

import org.POS.backend.product_category.AddProductCategoryRequestDto;
import org.POS.backend.product_category.ProductCategoryService;
import org.POS.backend.product_category.ProductCategoryStatus;
import org.POS.backend.product_category.UpdateProductCategoryRequestDto;
import org.junit.jupiter.api.Test;

public class ProductCategory {
    
    @Test
    void add(){
        ProductCategoryService productCategoryService = new ProductCategoryService();
        AddProductCategoryRequestDto dto = new AddProductCategoryRequestDto(
                "Gadgets",
                ProductCategoryStatus.ACTIVE,
                "No note"
        );
        productCategoryService.add(dto);
    }

    @Test
    void update(){
        ProductCategoryService productCategoryService = new ProductCategoryService();
        UpdateProductCategoryRequestDto dto = new UpdateProductCategoryRequestDto(
                1,
                "Appliancess",
                ProductCategoryStatus.ACTIVE,
                "No notes"
        );
        productCategoryService.update(dto);
    }

    @Test
    void delete(){
        ProductCategoryService productCategoryService = new ProductCategoryService();
        productCategoryService.delete(1);
    }

    @Test
    void getAllValidCategories(){
        ProductCategoryService productCategoryService = new ProductCategoryService();
        productCategoryService.getAllValidProductCategories().forEach(c -> {
            System.out.println(c.name());
        });
    }

    @Test
    void getValidCategory(){
        ProductCategoryService productCategoryService = new ProductCategoryService();
//        List<ProductSubcategory> subcategories = productCategoryService.getValidCategory(11).getSubcategories();
//        System.out.println(subcategories);
    }


}
