package org.POS.backend.expense;

import org.POS.backend.category.CategoryResponseDto;
import org.POS.backend.subcategory.SubcategoryResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseResponseDto(
        int id,
        SubcategoryResponseDto subcategory,
        String expenseReason,
        BigDecimal amount,
        String account,
        ExpenseStatus status,
        CategoryResponseDto category,
        LocalDate date
) {
}
