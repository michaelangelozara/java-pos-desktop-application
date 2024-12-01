package org.POS.backend.expense;

import org.POS.backend.expense_subcategory.ExpenseSubcategoryDAO;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ExpenseService {

    private ExpenseDAO expenseDAO;

    private ExpenseMapper expenseMapper;

    private UserDAO userDAO;


    private ExpenseSubcategoryDAO expenseSubcategoryDAO;

    public ExpenseService (){
        this.expenseDAO = new ExpenseDAO();
        this.expenseMapper = new ExpenseMapper();
        this.expenseSubcategoryDAO = new ExpenseSubcategoryDAO();
        this.userDAO = new UserDAO();
    }

    public String add(AddExpenseRequestDto dto){
        var subcategory = this.expenseSubcategoryDAO.getValidExpenseSubcategoryById(dto.subcategoryId());
        var user = this.userDAO.getUserById(CurrentUser.id);

        if(subcategory != null && user != null){
            var expense = this.expenseMapper.toExpense(dto, subcategory);

            UserLog userLog = new UserLog();
            userLog.setCode(expense.getCode());
            userLog.setDate(LocalDate.now());
            userLog.setAction(UserActionPrefixes.EXPENSES_ADD_ACTION_LOG_PREFIX);
            user.addUserLog(userLog);

            user.addExpense(expense);
            this.expenseDAO.add(expense, userLog);
            return GlobalVariable.EXPENSE_ADDED;
        }

        return GlobalVariable.SUBCATEGORY_NOT_FOUND;
    }

    public String update(UpdateExpenseRequestDto dto){
        var user = this.userDAO.getUserById(CurrentUser.id);
        var subcategory = this.expenseSubcategoryDAO.getValidExpenseSubcategoryById(dto.subcategoryId());
        if(subcategory != null && user != null){
            var expense = this.expenseDAO.getValidExpenseById(dto.expenseId());
            if(expense != null){
                var updatedExpense = this.expenseMapper.toUpdatedExpense(expense, dto, subcategory);

                UserLog userLog = new UserLog();
                userLog.setCode(updatedExpense.getCode());
                userLog.setDate(LocalDate.now());
                userLog.setAction(UserActionPrefixes.EXPENSE_CATEGORIES_EDIT_ACTION_LOG_PREFIX);
                user.addUserLog(userLog);

                this.expenseDAO.update(updatedExpense, userLog);
                return GlobalVariable.EXPENSE_UPDATED;
            }
            return GlobalVariable.EXPENSE_NOT_FOUND;
        }

        return GlobalVariable.SUBCATEGORY_NOT_FOUND;
    }

    public String delete(int expenseId){
        var user = this.userDAO.getUserById(CurrentUser.id);
        if(user == null)
            throw new RuntimeException(GlobalVariable.USER_NOT_FOUND);
        UserLog userLog = new UserLog();
        userLog.setDate(LocalDate.now());
        userLog.setAction(UserActionPrefixes.EXPENSES_REMOVE_ACTION_LOG_PREFIX);
        user.addUserLog(userLog);
        return this.expenseDAO.delete(expenseId, userLog);
    }

    public ExpenseResponseDto getValidExpenseById(int expenseId){
        return this.expenseMapper.expenseResponseDto(this.expenseDAO.getValidExpenseById(expenseId));
    }

    public List<ExpenseResponseDto> getAllValidExpenses(){
        return this.expenseMapper.expenseResponseDtoList(this.expenseDAO.getAllValidExpenses());
    }

    public List<ExpenseResponseDto> getAllValidExpenseByExpenseSubcategoryId(int number, int expenseSubcategoryId){
        return this.expenseMapper.expenseResponseDtoList(this.expenseDAO.getAllValidExpenseByExpenseSubcategoryId(number, expenseSubcategoryId));
    }

    public BigDecimal getTheSumOfExpenses(){
        return this.expenseDAO.getTheSumOfExpenses();
    }

    public List<ExpenseResponseDto> gatAllValidExpenseByReason(String reason){
        return this.expenseMapper.expenseResponseDtoList(this.expenseDAO.getAllValidExpenseByReason(reason));
    }

    public List<ExpenseResponseDto> getAllValidExpenseByRangeAndSubcategoryId(LocalDate start, LocalDate end, int subcategoryId){
        return this.expenseMapper.expenseResponseDtoList(this.expenseDAO.getAllValidExpenseByRangeAndSubcategoryId(start, end, subcategoryId));
    }

    public List<ExpenseResponseDto> getAllValidExpensesWithoutLimit(){
        return this.expenseMapper.expenseResponseDtoList(this.expenseDAO.getAllValidExpensesWithoutLimit());
    }
}
