package org.POS.backend.expense_category;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;

import java.util.List;

public class ExpenseCategoryMapper {

    private CodeGeneratorService codeGeneratorService;

    public ExpenseCategoryMapper(){
        this.codeGeneratorService = new CodeGeneratorService();
    }

    public ExpenseCategory toExpenseCategory(AddExpenseCategoryRequestDto dto){
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.setName(dto.name());
        expenseCategory.setStatus(dto.status());
        expenseCategory.setNote(dto.note());
        expenseCategory.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.EXPENSE_CATEGORY_PREFIX));
        return expenseCategory;
    }

    public ExpenseCategory toUpdatedExpenseCategory(ExpenseCategory expenseCategory, UpdateExpenseCategoryRequestDto dto){
        expenseCategory.setId(dto.expenseCategoryId());
        expenseCategory.setName(dto.name());
        expenseCategory.setStatus(dto.status());
        expenseCategory.setNote(dto.note());
        return expenseCategory;
    }

    public ExpenseCategoryResponseDto expenseCategoryResponseDto(ExpenseCategory expenseCategory){
        return new ExpenseCategoryResponseDto(
                expenseCategory.getId(),
                expenseCategory.getCode(),
                expenseCategory.getName(),
                expenseCategory.getNote(),
                expenseCategory.getStatus()
        );
    }

    public List<ExpenseCategoryResponseDto> expenseCategoryResponseDtoList(List<ExpenseCategory> expenseCategories){
        return expenseCategories
                .stream()
                .map(this::expenseCategoryResponseDto)
                .toList();
    }
}
