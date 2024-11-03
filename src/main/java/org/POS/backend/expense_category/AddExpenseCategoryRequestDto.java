package org.POS.backend.expense_category;

public record AddExpenseCategoryRequestDto(
        String name,
        ExpenseCategoryStatus status,
        String note
) {
}
