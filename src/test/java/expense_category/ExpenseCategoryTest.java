package expense_category;

import org.POS.backend.expense_category.*;
import org.junit.jupiter.api.Test;

public class ExpenseCategoryTest {

    @Test
    void add(){
        ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();

        AddExpenseCategoryRequestDto dto = new AddExpenseCategoryRequestDto(
                "Rent v3",
                ExpenseCategoryStatus.ACTIVE,
                "No note yet"
        );

        expenseCategoryService.add(dto);
    }

    @Test
    void update(){
        ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();
        UpdateExpenseCategoryRequestDto dto = new UpdateExpenseCategoryRequestDto(
                1,
                "Editeds",
                ExpenseCategoryStatus.ACTIVE,
                "No edited"
        );
        expenseCategoryService.update(dto);
    }

    @Test
    void delete(){
        ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();
        expenseCategoryService.delete(1);
    }

    @Test
    void getAllValidExpenseCategories(){
        ExpenseCategoryService expenseCategoryService = new ExpenseCategoryService();
        expenseCategoryService.getAllValidExpenseCategories().forEach(e ->{
            System.out.println(e.name());
        });
    }
}
