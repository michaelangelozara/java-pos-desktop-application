package org.POS.backend.expense_subcategory;

import org.POS.backend.expense_category.ExpenseCategoryDAO;
import org.POS.backend.expense_category.UpdateExpenseCategoryRequestDto;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;

import java.time.LocalDate;
import java.util.List;

public class ExpenseSubcategoryService {

    private ExpenseSubcategoryDAO expenseSubcategoryDAO;

    private ExpenseSubcategoryMapper expenseSubcategoryMapper;

    private ExpenseCategoryDAO expenseCategoryDAO;

    private UserDAO userDAO;

    public ExpenseSubcategoryService(){
        this.expenseSubcategoryDAO = new ExpenseSubcategoryDAO();
        this.expenseSubcategoryMapper = new ExpenseSubcategoryMapper();
        this.expenseCategoryDAO = new ExpenseCategoryDAO();
        this.userDAO = new UserDAO();
    }

    public String add(AddExpenseSubcategoryRequestDto dto){
        var expenseCategory = this.expenseCategoryDAO.getValidExpenseCategoryById(dto.expenseCategoryId());

        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        if(expenseCategory != null){
            var expenseSubcategory = this.expenseSubcategoryMapper.toExpenseSubcategory(dto, expenseCategory);

            UserLog userLog = new UserLog();
            userLog.setCode(expenseSubcategory.getCode());
            userLog.setDate(LocalDate.now());
            userLog.setAction(UserActionPrefixes.EXPENSE_SUB_CATEGORIES_ADD_ACTION_LOG_PREFIX);
            user.addUserLog(userLog);

            this.expenseSubcategoryDAO.add(expenseSubcategory, userLog);
            return GlobalVariable.EXPENSE_SUBCATEGORY_ADDED;
        }

        return GlobalVariable.EXPENSE_CATEGORY_NOT_FOUND;
    }

    public String update(UpdateExpenseSubcategoryRequestDto dto){
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        var expenseCategory = this.expenseCategoryDAO.getValidExpenseCategoryById(dto.expenseCategoryId());
        if(expenseCategory != null && user != null) {
            var expenseSubcategory = this.expenseSubcategoryDAO.getValidExpenseSubcategoryById(dto.expenseSubcategoryId());
            if (expenseSubcategory != null) {
                var updatedExpenseSubcategory = this.expenseSubcategoryMapper.toUpdatedExpenseSubcategory(expenseSubcategory, dto, expenseCategory);

                UserLog userLog = new UserLog();
                userLog.setCode(updatedExpenseSubcategory.getCode());
                userLog.setDate(LocalDate.now());
                userLog.setAction(UserActionPrefixes.EXPENSE_SUB_CATEGORIES_EDIT_ACTION_LOG_PREFIX);
                user.addUserLog(userLog);

                this.expenseSubcategoryDAO.update(updatedExpenseSubcategory, userLog);
                return GlobalVariable.EXPENSE_SUBCATEGORY_UPDATED;
            }
            return GlobalVariable.EXPENSE_SUBCATEGORY_NOT_FOUND;
        }
        return GlobalVariable.EXPENSE_CATEGORY_UPDATED;
    }

    public String delete(int expenseCategoryId){
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        UserLog userLog = new UserLog();
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.EXPENSE_SUB_CATEGORIES_REMOVE_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        this.expenseSubcategoryDAO.delete(expenseCategoryId, userLog);
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

    public List<ExpenseSubcategoryResponseDto> getAllValidExpenseSubcategoryByExpenseCategoryId(int expenseCategoryId){
        return this.expenseSubcategoryMapper.expenseSubcategoryResponseDtoList(this.expenseSubcategoryDAO.getAllValidExpenseSubcategoryByExpenseCategoryId(expenseCategoryId));
    }

    public List<ExpenseSubcategoryResponseDto> getAllValidExpenseSubcategoryByName(String name){
        return this.expenseSubcategoryMapper.expenseSubcategoryResponseDtoList(this.expenseSubcategoryDAO.getAllValidExpenseSubcategoryByName(name));
    }
}
