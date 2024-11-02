package category;

import org.POS.backend.category.*;
import org.POS.backend.subcategory.Subcategory;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Category {
    
    @Test
    void add(){
        CategoryService categoryService = new CategoryService();
        AddCategoryRequestDto dto = new AddCategoryRequestDto(
                "Gadgets",
                CategoryStatus.ACTIVE,
                "No note"
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
        List<Subcategory> subcategories = categoryService.getValidCategory(11).getSubcategories();
        System.out.println(subcategories);
    }


}
