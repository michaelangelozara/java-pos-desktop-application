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

    public ExpenseMapper(){
        this.expenseSubcategoryMapper = new ExpenseSubcategoryMapper();
        this.codeGeneratorService = new CodeGeneratorService();
    }

    public Expense toExpense(AddExpenseRequestDto dto, ExpenseSubcategory expenseSubcategory){
        Expense expense = new Expense();
        expense.setExpenseSubcategory(expenseSubcategory);
        expense.setExpenseReason(dto.expenseReason());
        expense.setAmount(dto.amount());
        expense.setChequeNo(dto.chequeNo());
        expense.setVoucherNo(dto.voucherNo());
        expense.setNote(dto.note());
        expense.setAccount(dto.account());
        expense.setDate(LocalDate.now());
        expense.setStatus(dto.status());
        expense.setImage(dto.image());
        expense.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.EXPENSE_CATEGORY_PREFIX));
        return expense;
    }

    public Expense toUpdatedExpense(Expense expense, UpdateExpenseRequestDto dto, ExpenseSubcategory expenseSubcategory){
        expense.setExpenseSubcategory(expenseSubcategory);
        expense.setExpenseReason(dto.expenseReason());
        expense.setAmount(dto.amount());
        expense.setAccount(dto.account());
        expense.setStatus(dto.status());
        return expense;
    }

    public ExpenseResponseDto expenseResponseDto(Expense expense){
        return new ExpenseResponseDto(
                expense.getId(),
                this.expenseSubcategoryMapper.expenseSubcategoryResponseDto(expense.getExpenseSubcategory()),
                expense.getExpenseReason(),
                expense.getAmount(),
                expense.getAccount(),
                expense.getStatus(),
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
