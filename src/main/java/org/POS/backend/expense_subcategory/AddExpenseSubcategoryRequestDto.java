package org.POS.backend.expense_subcategory;

public record AddExpenseSubcategoryRequestDto(
        String name,
        ExpenseSubcategoryStatus status,
        String note,
        int expenseCategoryId
) {
}
