package org.POS.backend.pos_cash_transaction;

import org.POS.backend.user.User;

import java.math.BigDecimal;

public class PosCashTransaction {

    private Integer id;

    private BigDecimal amount;

    private String note;

    private TransactionType type;

    private User user;
}
