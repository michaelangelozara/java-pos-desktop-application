package org.POS.backend.expense;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.expense_subcategory.ExpenseSubcategory;
import org.POS.backend.expense_subcategory.ExpenseSubcategoryMapper;
import org.POS.backend.global_variable.GlobalVariable;

import java.time.LocalDate;
import java.util.List;

public class ExpenseMapper {

    private CodeGeneratorService codeGeneratorService;

    private ExpenseSubcategoryMapper expenseSubcategoryMapper;

    public ExpenseMapper() {
        this.expenseSubcategoryMapper = new ExpenseSubcategoryMapper();
        this.codeGeneratorService = new CodeGeneratorService();
    }

    public Expense toExpense(AddExpenseRequestDto dto, ExpenseSubcategory expenseSubcategory) {
        Expense expense = new Expense();
        expense.setExpenseSubcategory(expenseSubcategory);
        expense.setExpenseReason(dto.expenseReason());
        expense.setAmount(dto.amount());
        expense.setNote(dto.note());
        expense.setStatus(dto.status());
        expense.setCreatedAt(LocalDate.now());
        expense.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.EXPENSE_CATEGORY_PREFIX));
        return expense;
    }

    public Expense toUpdatedExpense(Expense expense, UpdateExpenseRequestDto dto, ExpenseSubcategory expenseSubcategory) {
        expense.setExpenseSubcategory(expenseSubcategory);
        expense.setExpenseReason(dto.expenseReason());
        expense.setAmount(dto.amount());
        expense.setStatus(dto.status());
        return expense;
    }

    public ExpenseResponseDto expenseResponseDto(Expense expense) {
        return new ExpenseResponseDto(
                expense.getId(),
                expense.getCreatedAt(),
                expense.getExpenseReason(),
                expense.getExpenseSubcategory().getExpenseCategory().getName(),
                expense.getExpenseSubcategory().getName(),
                expense.getAmount(),
                expense.getStatus(),
                expense.getUser().getName()
        );
    }

    public List<ExpenseResponseDto> expenseResponseDtoList(List<Expense> expenses) {
        return expenses
                .stream()
                .map(this::expenseResponseDto)
                .toList();
    }
}
