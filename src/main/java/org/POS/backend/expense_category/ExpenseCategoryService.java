package org.POS.backend.expense_category;

import org.POS.backend.global_variable.GlobalVariable;

import java.util.List;

public class ExpenseCategoryService {

    private ExpenseCategoryDAO expenseCategoryDAO;

    private ExpenseCategoryMapper expenseCategoryMapper;

    public ExpenseCategoryService(){
        this.expenseCategoryDAO = new ExpenseCategoryDAO();
        this.expenseCategoryMapper = new ExpenseCategoryMapper();
    }

    public String add(AddExpenseCategoryRequestDto dto){
        var expenseCategory = this.expenseCategoryMapper.toExpenseCategory(dto);
        this.expenseCategoryDAO.add(expenseCategory);
        return GlobalVariable.EXPENSE_CATEGORY_ADDED;
    }

    public String update(UpdateExpenseCategoryRequestDto dto){
        var expenseCategory = this.expenseCategoryDAO.getValidExpenseCategoryById(dto.expenseCategoryId());
        if(expenseCategory != null){
            var updatedExpenseCategory = this.expenseCategoryMapper.toUpdatedExpenseCategory(expenseCategory, dto);
            this.expenseCategoryDAO.update(updatedExpenseCategory);
            return GlobalVariable.EXPENSE_CATEGORY_UPDATED;
        }
        return GlobalVariable.EXPENSE_CATEGORY_NOT_FOUND;
    }

    public String delete(int expenseCategoryId){
        this.expenseCategoryDAO.delete(expenseCategoryId);
        return GlobalVariable.EXPENSE_CATEGORY_DELETED;
    }

    public List<ExpenseCategoryResponseDto> getAllValidExpenseCategories(){
        return
                this.expenseCategoryMapper
                        .expenseCategoryResponseDtoList(this.expenseCategoryDAO.getAllValidExpenseCategories());
    }
}
