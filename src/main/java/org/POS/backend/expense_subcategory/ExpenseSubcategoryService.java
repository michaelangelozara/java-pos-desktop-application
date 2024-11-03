package org.POS.backend.expense_subcategory;

import org.POS.backend.expense_category.ExpenseCategoryDAO;
import org.POS.backend.expense_category.UpdateExpenseCategoryRequestDto;
import org.POS.backend.global_variable.GlobalVariable;

import java.util.List;

public class ExpenseSubcategoryService {

    private ExpenseSubcategoryDAO expenseSubcategoryDAO;

    private ExpenseSubcategoryMapper expenseSubcategoryMapper;

    private ExpenseCategoryDAO expenseCategoryDAO;

    public ExpenseSubcategoryService(){
        this.expenseSubcategoryDAO = new ExpenseSubcategoryDAO();
        this.expenseSubcategoryMapper = new ExpenseSubcategoryMapper();
        this.expenseCategoryDAO = new ExpenseCategoryDAO();
    }

    public String add(AddExpenseSubcategoryRequestDto dto){
        var expenseCategory = this.expenseCategoryDAO.getValidExpenseCategoryById(dto.expenseCategoryId());

        if(expenseCategory != null){
            var expenseSubcategory = this.expenseSubcategoryMapper.toExpenseSubcategory(dto, expenseCategory);
            this.expenseSubcategoryDAO.add(expenseSubcategory);
            return GlobalVariable.EXPENSE_SUBCATEGORY_ADDED;
        }

        return GlobalVariable.EXPENSE_CATEGORY_NOT_FOUND;
    }

    public String update(UpdateExpenseSubcategoryRequestDto dto){
        var expenseCategory = this.expenseCategoryDAO.getValidExpenseCategoryById(dto.expenseCategoryId());
        if(expenseCategory != null) {
            var expenseSubcategory = this.expenseSubcategoryDAO.getValidExpenseSubcategoryById(dto.expenseSubcategoryId());
            if (expenseSubcategory != null) {
                var updatedExpenseCategory = this.expenseSubcategoryMapper.toUpdatedExpenseSubcategory(expenseSubcategory, dto, expenseCategory);
                this.expenseSubcategoryDAO.update(updatedExpenseCategory);
                return GlobalVariable.EXPENSE_SUBCATEGORY_UPDATED;
            }
            return GlobalVariable.EXPENSE_SUBCATEGORY_NOT_FOUND;
        }
        return GlobalVariable.EXPENSE_CATEGORY_UPDATED;
    }

    public String delete(int expenseCategoryId){
        this.expenseSubcategoryDAO.delete(expenseCategoryId);
        return GlobalVariable.EXPENSE_SUBCATEGORY_DELETED;
    }

    public List<ExpenseSubcategoryResponseDto> getAllValidExpenseSubcategories(){
        return
                this.expenseSubcategoryMapper
                        .expenseSubcategoryResponseDtoList(
                                this.expenseSubcategoryDAO.getAllValidExpenseSubcategories()
                        );
    }

    public ExpenseSubcategoryResponseDto getValidExpenseSubcategoryById(
            int expenseSubcategoryId
    ){
        return
                this.expenseSubcategoryMapper
                        .expenseSubcategoryResponseDto(
                                this.expenseSubcategoryDAO
                                        .getValidExpenseSubcategoryById(expenseSubcategoryId)
                        );
    }

    public List<ExpenseSubcategoryResponseDto> getAllValidExpenseSubcategoriesByExpenseCategoryId(
            int expenseCategoryId
    ){
        return
                this.expenseSubcategoryMapper
                        .expenseSubcategoryResponseDtoList(
                                this.expenseSubcategoryDAO.getAllValidExpenseSubcategoriesByExpenseCategoryId(expenseCategoryId)
                        );
    }
}
