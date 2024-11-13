package org.POS.backend.expense;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseResponseDto(
        int id,
        LocalDate createdAt,
        String expenseReason,
        String category,
        String subcategory,
        BigDecimal amount,
        String account,
        ExpenseStatus status,
        String createdBy
) {
}
