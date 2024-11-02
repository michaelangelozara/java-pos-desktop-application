package org.POS.backend.expense;

import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.subcategory.SubcategoryDAO;

import java.util.List;

public class ExpenseService {

    private ExpenseDAO expenseDAO;

    private ExpenseMapper expenseMapper;

    private SubcategoryDAO subcategoryDAO;

    public ExpenseService (){
        this.expenseDAO = new ExpenseDAO();
        this.expenseMapper = new ExpenseMapper();
        this.subcategoryDAO = new SubcategoryDAO();
    }

    public String add(AddExpenseRequestDto dto){
        var subcategory = this.subcategoryDAO.getValidSubcategoryById(dto.subcategoryId());
        if(subcategory != null){
            var expense = this.expenseMapper.toExpense(dto, subcategory);
            this.expenseDAO.add(expense);
            return GlobalVariable.EXPENSE_ADDED;
        }

        return GlobalVariable.SUBCATEGORY_NOT_FOUND;
    }

    public String update(UpdateExpenseRequestDto dto){
        var subcategory = this.subcategoryDAO.getValidSubcategoryById(dto.subcategoryId());
        if(subcategory != null){
            var expense = this.expenseMapper.toUpdatedExpense(dto, subcategory);
            this.expenseDAO.update(expense);
            return GlobalVariable.EXPENSE_UPDATED;
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
