package org.POS.backend.cash_transaction;

import java.util.List;

public class CashTransactionService {

    private CashTransactionDAO cashTransactionDAO;

    private CashTransactionMapper cashTransactionMapper;

    public CashTransactionService(){
        this.cashTransactionDAO = new CashTransactionDAO();
        this.cashTransactionMapper = new CashTransactionMapper();
    }

    public List<CashTransactionResponseDto> getAllValidCashTransactions(int number){
        return this.cashTransactionMapper.toCashTransactionResponseDtoList(this.cashTransactionDAO.getAllValidCashTransaction(number));
    }

    public List<CashTransactionResponseDto> getAllValidCashTransactionsByUserName(String name){
        return this.cashTransactionMapper.toCashTransactionResponseDtoList(this.cashTransactionDAO.getAllValidCashTransactionByUserName(name));
    }
}
