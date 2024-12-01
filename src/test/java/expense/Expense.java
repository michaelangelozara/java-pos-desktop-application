package expense;

import org.POS.backend.expense.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class Expense {

    @Test
    void add(){
//        AddExpenseRequestDto dto = new AddExpenseRequestDto(
//                2,
//                "Bought a lot of phones",
//                BigDecimal.valueOf(100000),
//                "No Account yet",
//                "1231234786",
//                "12314",
//                "No note yet",
//                ExpenseStatus.ACTIVE
//        );
//
//        ExpenseService expenseService = new ExpenseService();
//        expenseService.add(dto);
    }

    @Test
    void update(){
//        UpdateExpenseRequestDto dto = new UpdateExpenseRequestDto(
//                1,
//                "No Reasons",
//                BigDecimal.valueOf(10003),
//                "No Account 2",
//                ExpenseStatus.INACTIVE,
//                1
//        );
//
//        ExpenseService expenseService = new ExpenseService();
//        expenseService.update(dto);
    }

    @Test
    void delete(){
        ExpenseService expenseService = new ExpenseService();
        expenseService.delete(1);
    }

    @Test
    void getValidExpense(){
        ExpenseService expenseService = new ExpenseService();
        System.out.println(expenseService.getValidExpenseById(2));
    }

    @Test
    void getAllValidExpenses(){
        ExpenseService expenseService = new ExpenseService();
        System.out.println(expenseService.getAllValidExpenses());
    }
}
