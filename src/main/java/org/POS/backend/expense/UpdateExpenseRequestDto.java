package org.POS.backend.expense;

import java.math.BigDecimal;

public record UpdateExpenseRequestDto(
        int subcategoryId,
        String expenseReason,
        BigDecimal amount,
        ExpenseStatus status,
        int expenseId
) {
}
