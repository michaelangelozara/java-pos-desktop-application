package org.POS.backend.expense_subcategory;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.expense_category.ExpenseCategory;
import org.POS.backend.expense_category.ExpenseCategoryMapper;
import org.POS.backend.expense_category.UpdateExpenseCategoryRequestDto;
import org.POS.backend.global_variable.GlobalVariable;

import java.util.List;

public class ExpenseSubcategoryMapper {

    private CodeGeneratorService codeGeneratorService;

    private ExpenseCategoryMapper expenseCategoryMapper;

    public ExpenseSubcategoryMapper(){
        this.codeGeneratorService = new CodeGeneratorService();
        this.expenseCategoryMapper = new ExpenseCategoryMapper();
    }

    public ExpenseSubcategory toExpenseSubcategory(AddExpenseSubcategoryRequestDto dto, ExpenseCategory expenseCategory){
        ExpenseSubcategory expenseSubcategory = new ExpenseSubcategory();
        expenseSubcategory.setExpenseCategory(expenseCategory);
        expenseSubcategory.setName(dto.name());
        expenseSubcategory.setStatus(dto.status());
        expenseSubcategory.setNote(dto.note());
        expenseSubcategory.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.EXPENSE_SUBCATEGORY_PREFIX));
        return expenseSubcategory;
    }

    public ExpenseSubcategory toUpdatedExpenseSubcategory(ExpenseSubcategory expenseSubcategory, UpdateExpenseSubcategoryRequestDto dto, ExpenseCategory expenseCategory){
        expenseSubcategory.setId(dto.expenseSubcategoryId());
        expenseSubcategory.setExpenseCategory(expenseCategory);
        expenseSubcategory.setId(dto.expenseCategoryId());
        expenseSubcategory.setName(dto.name());
        expenseSubcategory.setStatus(dto.status());
        expenseSubcategory.setNote(dto.note());
        return expenseSubcategory;
    }

    public ExpenseSubcategoryResponseDto expenseSubcategoryResponseDto(ExpenseSubcategory expenseSubcategory){
        return new ExpenseSubcategoryResponseDto(
                expenseSubcategory.getId(),
                this.expenseCategoryMapper.expenseCategoryResponseDto(expenseSubcategory.getExpenseCategory()),
                expenseSubcategory.getCode(),
                expenseSubcategory.getName(),
                expenseSubcategory.getStatus()
        );
    }

    public List<ExpenseSubcategoryResponseDto> expenseSubcategoryResponseDtoList(List<ExpenseSubcategory> expenseSubcategories){
        return expenseSubcategories
                .stream()
                .map(this::expenseSubcategoryResponseDto)
                .toList();
    }
}
