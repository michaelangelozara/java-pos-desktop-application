package org.POS.backend.expense_category;

public record ExpenseCategoryResponseDto(
        int id,
        String code,
        String name,
        String note,
        ExpenseCategoryStatus status
) {
}
