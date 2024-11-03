package org.POS.backend.expense;

import org.POS.backend.expense_subcategory.ExpenseSubcategoryDAO;
import org.POS.backend.global_variable.GlobalVariable;

import java.util.List;

public class ExpenseService {

    private ExpenseDAO expenseDAO;

    private ExpenseMapper expenseMapper;


    private ExpenseSubcategoryDAO expenseSubcategoryDAO;

    public ExpenseService (){
        this.expenseDAO = new ExpenseDAO();
        this.expenseMapper = new ExpenseMapper();
        this.expenseSubcategoryDAO = new ExpenseSubcategoryDAO();
    }

    public String add(AddExpenseRequestDto dto){
        var subcategory = this.expenseSubcategoryDAO.getValidExpenseSubcategoryById(dto.subcategoryId());
        if(subcategory != null){
            var expense = this.expenseMapper.toExpense(dto, subcategory);
            this.expenseDAO.add(expense);
            return GlobalVariable.EXPENSE_ADDED;
        }

        return GlobalVariable.SUBCATEGORY_NOT_FOUND;
    }

    public String update(UpdateExpenseRequestDto dto){
        var subcategory = this.expenseSubcategoryDAO.getValidExpenseSubcategoryById(dto.subcategoryId());
        if(subcategory != null){
            var expense = this.expenseDAO.getValidExpenseById(dto.expenseId());
            if(expense != null){
                var updatedExpense = this.expenseMapper.toUpdatedExpense(expense, dto, subcategory);
                this.expenseDAO.update(updatedExpense);
                return GlobalVariable.EXPENSE_UPDATED;
            }
            return GlobalVariable.EXPENSE_NOT_FOUND;
        }

        return GlobalVariable.SUBCATEGORY_NOT_FOUND;
    }

    public String delete(int expenseId){
        return this.expenseDAO.delete(expenseId);
    }

    public ExpenseResponseDto getValidExpenseById(int expenseId){
        return this.expenseMapper.expenseResponseDto(this.expenseDAO.getValidExpenseById(expenseId));
    }

    public List<ExpenseResponseDto> getAllValidExpenses(){
        return this.expenseMapper.expenseResponseDtoList(this.expenseDAO.getAllValidExpenses());
    }
}
