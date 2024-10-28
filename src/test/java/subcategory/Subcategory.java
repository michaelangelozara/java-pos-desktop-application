package subcategory;

import org.POS.backend.subcategory.AddSubcategoryRequestDto;
import org.POS.backend.subcategory.SubcategoryService;
import org.POS.backend.subcategory.SubcategoryStatus;
import org.POS.backend.subcategory.UpdateSubcategoryRequestDto;
import org.junit.jupiter.api.Test;

public class Subcategory {

    @Test
    void add(){
        SubcategoryService service = new SubcategoryService();
        AddSubcategoryRequestDto dto = new AddSubcategoryRequestDto(
                2,
                "Toyota",
                SubcategoryStatus.ACTIVE,
                "This is car category!"
        );
        service.add(dto);
    }

    @Test
    void update(){
        SubcategoryService service = new SubcategoryService();
        UpdateSubcategoryRequestDto dto = new UpdateSubcategoryRequestDto(
                1,
                "Cellphone",
                SubcategoryStatus.ACTIVE,
                "This is cellphone gago !!!",
                3
        );
        service.update(dto);
    }

    @Test
    void delete(){
        SubcategoryService service = new SubcategoryService();
        service.delete(2);
    }


    @Test
    void getAllValidSubcategories(){
        SubcategoryService service = new SubcategoryService();
        service.getAllValidSubcategories().forEach(s -> {
            System.out.println(s.name());
        });
    }

    @Test
    void getValidSubcategory(){
        SubcategoryService service = new SubcategoryService();
        System.out.println(service.getValidSubcategory(2));
    }
}
