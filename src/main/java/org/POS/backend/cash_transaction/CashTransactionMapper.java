package org.POS.backend.cash_transaction;

import org.POS.backend.date_time_formatter.CustomDateTimeFormatter;

import java.util.List;

public class CashTransactionMapper {

    public CashTransactionResponseDto toCashTransactionResponseDto(CashTransaction cashTransaction){
        return new CashTransactionResponseDto(
                cashTransaction.getId(),
                cashTransaction.getReference(),
                cashTransaction.getCashIn(),
                cashTransaction.getCashOut(),
                cashTransaction.getTransactionPaymentMethod(),
                cashTransaction.getUser().getName(),
                CustomDateTimeFormatter.getLocalDateTime(cashTransaction.getDateTime())
        );
    }

    public List<CashTransactionResponseDto> toCashTransactionResponseDtoList(List<CashTransaction> cashTransactions){
        return cashTransactions
                .stream()
                .map(this::toCashTransactionResponseDto)
                .toList();
    }
}
