package org.POS.backend.expense_category;

import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;

import java.time.LocalDate;
import java.util.List;

public class ExpenseCategoryService {

    private ExpenseCategoryDAO expenseCategoryDAO;

    private ExpenseCategoryMapper expenseCategoryMapper;

    private UserDAO userDAO;

    public ExpenseCategoryService(){
        this.expenseCategoryDAO = new ExpenseCategoryDAO();
        this.expenseCategoryMapper = new ExpenseCategoryMapper();
        this.userDAO = new UserDAO();
    }

    public String add(AddExpenseCategoryRequestDto dto){
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        var expenseCategory = this.expenseCategoryMapper.toExpenseCategory(dto);
        UserLog userLog = new UserLog();
        userLog.setCode(expenseCategory.getCode());
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.EXPENSE_CATEGORIES_ADD_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        this.expenseCategoryDAO.add(expenseCategory, userLog);
        return GlobalVariable.EXPENSE_CATEGORY_ADDED;
    }

    public String update(UpdateExpenseCategoryRequestDto dto){
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        var expenseCategory = this.expenseCategoryDAO.getValidExpenseCategoryById(dto.expenseCategoryId());
        if(expenseCategory != null){
            var updatedExpenseCategory = this.expenseCategoryMapper.toUpdatedExpenseCategory(expenseCategory, dto);

            UserLog userLog = new UserLog();
            userLog.setCode(expenseCategory.getCode());
            userLog.setDate(LocalDate.now());
            userLog.setAction(UserActionPrefixes.EXPENSE_CATEGORIES_EDIT_ACTION_LOG_PREFIX);
            user.addUserLog(userLog);

            this.expenseCategoryDAO.update(updatedExpenseCategory, userLog);
            return GlobalVariable.EXPENSE_CATEGORY_UPDATED;
        }
        return GlobalVariable.EXPENSE_CATEGORY_NOT_FOUND;
    }

    public String delete(int expenseCategoryId){
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);

        UserLog userLog = new UserLog();
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.EXPENSE_CATEGORIES_REMOVE_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);

        this.expenseCategoryDAO.delete(expenseCategoryId, userLog);
        return GlobalVariable.EXPENSE_CATEGORY_DELETED;
    }

    public List<ExpenseCategoryResponseDto> getAllValidExpenseCategories(){
        return
                this.expenseCategoryMapper
                        .expenseCategoryResponseDtoList(this.expenseCategoryDAO.getAllValidExpenseCategories());
    }

    public List<ExpenseCategoryResponseDto> getAllValidExpenseCategoryByName(String name){
        return this.expenseCategoryMapper.expenseCategoryResponseDtoList(this.expenseCategoryDAO.getAllValidExpenseCategoryByName(name));
    }
}
