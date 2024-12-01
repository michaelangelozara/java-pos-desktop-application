package org.POS.backend.expense;

import java.math.BigDecimal;

public record AddExpenseRequestDto(
        int subcategoryId,
        String expenseReason,
        BigDecimal amount,
        String note,
        ExpenseStatus status
) {
}
