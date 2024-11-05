package product_subcategory;

import org.POS.backend.product_subcategory.AddProductSubcategoryRequestDto;
import org.POS.backend.product_subcategory.ProductSubcategoryService;
import org.POS.backend.product_subcategory.ProductSubcategoryStatus;
import org.POS.backend.product_subcategory.UpdateProductSubcategoryRequestDto;
import org.junit.jupiter.api.Test;

public class ProductSubcategory {

    @Test
    void add(){
        ProductSubcategoryService service = new ProductSubcategoryService();
//        AddProductSubcategoryRequestDto dto = new AddProductSubcategoryRequestDto(
//                1,
//                "Phone",
//                ProductSubcategoryStatus.ACTIVE,
//                "This is car category!",
//                "code"
//        );
//        service.add(dto);
    }

    @Test
    void update(){
        ProductSubcategoryService service = new ProductSubcategoryService();
//        UpdateProductSubcategoryRequestDto dto = new UpdateProductSubcategoryRequestDto(
//                1,
//                "Cellphone",
//                ProductSubcategoryStatus.ACTIVE,
//                "This is cellphone gago !!!",
//                "code",
//                3
//        );
//        service.update(dto);
    }

    @Test
    void delete(){
        ProductSubcategoryService service = new ProductSubcategoryService();
        service.delete(2);
    }


    @Test
    void getAllValidSubcategories(){
        ProductSubcategoryService service = new ProductSubcategoryService();
        service.getAllValidSubcategories().forEach(s -> {
            System.out.println(s.name());
        });
    }

    @Test
    void getValidSubcategory(){
        ProductSubcategoryService service = new ProductSubcategoryService();
        System.out.println(service.getValidSubcategory(2));
    }
}
