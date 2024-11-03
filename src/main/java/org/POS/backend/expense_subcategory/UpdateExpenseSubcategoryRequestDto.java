package org.POS.backend.expense_subcategory;

public record UpdateExpenseSubcategoryRequestDto(
        int expenseSubcategoryId,
        String name,
        ExpenseSubcategoryStatus status,
        String note,
        int expenseCategoryId
) {
}
