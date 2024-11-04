package expense_subcategory;

import org.POS.backend.expense_subcategory.AddExpenseSubcategoryRequestDto;
import org.POS.backend.expense_subcategory.ExpenseSubcategoryService;
import org.POS.backend.expense_subcategory.ExpenseSubcategoryStatus;
import org.POS.backend.expense_subcategory.UpdateExpenseSubcategoryRequestDto;
import org.junit.jupiter.api.Test;

public class ExpenseSubcategory {

    @Test
    void add(){
        ExpenseSubcategoryService subcategoryService = new ExpenseSubcategoryService();
        AddExpenseSubcategoryRequestDto dto = new AddExpenseSubcategoryRequestDto(
                "Bagong Kategorya",
                ExpenseSubcategoryStatus.ACTIVE,
                "No note",
                1
        );
        subcategoryService.add(dto);
    }

    @Test
    void update(){
        ExpenseSubcategoryService subcategoryService = new ExpenseSubcategoryService();
        UpdateExpenseSubcategoryRequestDto dto = new UpdateExpenseSubcategoryRequestDto(
                1,
                "Exp Subsss",
                ExpenseSubcategoryStatus.ACTIVE,
                "No notesss",
                1
        );
        subcategoryService.update(dto);
    }

    @Test
    void delete(){
        ExpenseSubcategoryService subcategoryService = new ExpenseSubcategoryService();
        subcategoryService.delete(2);
    }

    @Test
    void getAllValidExpenseSubcategories(){
        ExpenseSubcategoryService subcategoryService = new ExpenseSubcategoryService();

    }

    @Test
    void getValidExpenseSubcategory(){
        ExpenseSubcategoryService subcategoryService = new ExpenseSubcategoryService();
        System.out.println(subcategoryService.getValidExpenseSubcategoryById(1));
    }
}
