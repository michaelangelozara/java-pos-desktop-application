package org.POS.backend.expense;

import org.POS.backend.category.CategoryMapper;
import org.POS.backend.subcategory.Subcategory;
import org.POS.backend.subcategory.SubcategoryMapper;

import java.time.LocalDate;
import java.util.List;

public class ExpenseMapper {

    private SubcategoryMapper subcategoryMapper;

    private CategoryMapper categoryMapper;

    public ExpenseMapper(){
        this.subcategoryMapper = new SubcategoryMapper();
        this.categoryMapper = new CategoryMapper();
    }

    public Expense toExpense(AddExpenseRequestDto dto, Subcategory subcategory){
        Expense expense = new Expense();
        expense.setSubcategory(subcategory);
        expense.setExpenseReason(dto.expenseReason());
        expense.setAmount(dto.amount());
        expense.setAccount(dto.account());
        expense.setDate(LocalDate.now());
        expense.setStatus(dto.status());
        return expense;
    }

    public Expense toUpdatedExpense(UpdateExpenseRequestDto dto, Subcategory subcategory){
        Expense expense = new Expense();
        expense.setId(dto.expenseId());
        expense.setSubcategory(subcategory);
        expense.setExpenseReason(dto.expenseReason());
        expense.setAmount(dto.amount());
        expense.setAccount(dto.account());
        expense.setDate(LocalDate.now());
        expense.setStatus(dto.status());
        return expense;
    }

    public ExpenseResponseDto expenseResponseDto(Expense expense){
        return new ExpenseResponseDto(
                expense.getId(),
                this.subcategoryMapper.subcategoryResponseDto(expense.getSubcategory()),
                expense.getExpenseReason(),
                expense.getAmount(),
                expense.getAccount(),
                expense.getStatus(),
                this.categoryMapper.categoryResponseDto(expense.getSubcategory().getCategory()),
                expense.getDate()
        );
    }

    public List<ExpenseResponseDto> expenseResponseDtoList(List<Expense> expenses){
        return expenses
                .stream()
                .map(this::expenseResponseDto)
                .toList();
    }
}
