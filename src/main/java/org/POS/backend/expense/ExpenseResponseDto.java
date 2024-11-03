package org.POS.backend.expense;

import org.POS.backend.expense_subcategory.ExpenseSubcategoryResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseResponseDto(
        int id,
        ExpenseSubcategoryResponseDto subcategoryResponseDto,
        String expenseReason,
        BigDecimal amount,
        String account,
        ExpenseStatus status,
        LocalDate date
) {
}
