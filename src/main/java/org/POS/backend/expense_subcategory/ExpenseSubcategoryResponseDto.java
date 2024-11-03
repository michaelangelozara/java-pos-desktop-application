package org.POS.backend.expense_subcategory;

import org.POS.backend.expense_category.ExpenseCategoryResponseDto;

public record ExpenseSubcategoryResponseDto(
        int id,
        ExpenseCategoryResponseDto expenseCategoryResponseDto,
        String code,
        String name,
        ExpenseSubcategoryStatus status
) {
}
