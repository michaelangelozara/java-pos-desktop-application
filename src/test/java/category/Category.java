package category;

import org.POS.backend.category.AddCategoryRequestDto;
import org.POS.backend.category.CategoryService;
import org.POS.backend.category.CategoryStatus;
import org.POS.backend.category.UpdateCategoryRequestDto;
import org.junit.jupiter.api.Test;

public class Category {

    @Test
    void add(){
        CategoryService categoryService = new CategoryService();
        AddCategoryRequestDto dto = new AddCategoryRequestDto(
                "Cars",
                CategoryStatus.ACTIVE,
                "No note 1"
        );
        categoryService.add(dto);
    }

    @Test
    void update(){
        CategoryService categoryService = new CategoryService();
        UpdateCategoryRequestDto dto = new UpdateCategoryRequestDto(
                1,
                "Appliancess",
                CategoryStatus.ACTIVE,
                "No notes"
        );
        categoryService.update(dto);
    }

    @Test
    void delete(){
        CategoryService categoryService = new CategoryService();
        categoryService.delete(1);
    }

    @Test
    void getAllValidCategories(){
        CategoryService categoryService = new CategoryService();
        categoryService.getAllValidCategories().forEach(c -> {
            System.out.println(c.name());
        });
    }

    @Test
    void getValidCategory(){
        CategoryService categoryService = new CategoryService();
        System.out.println(categoryService.getValidCategory(2));
    }


}
