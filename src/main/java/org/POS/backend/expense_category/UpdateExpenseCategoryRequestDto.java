package org.POS.backend.expense_category;


public record UpdateExpenseCategoryRequestDto(
        int expenseCategoryId,
        String name,
        ExpenseCategoryStatus status,
        String note
) {
}
