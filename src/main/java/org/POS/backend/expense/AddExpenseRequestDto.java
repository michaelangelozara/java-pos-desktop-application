package org.POS.backend.expense;

import java.math.BigDecimal;

public record AddExpenseRequestDto(
        int subcategoryId,
        String expenseReason,
        BigDecimal amount,
        String account,
        String chequeNo,
        String voucherNo,
        String note,
        ExpenseStatus status,
        String image
) {
}
