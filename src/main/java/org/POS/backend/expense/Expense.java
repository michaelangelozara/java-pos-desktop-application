package org.POS.backend.expense;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.POS.backend.expense_subcategory.ExpenseSubcategory;
import org.POS.backend.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "expense_reason")
    private String expenseReason;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "created_at")
    private LocalDate createdAt;

    private String account;

    @Column(name = "cheque_no")
    private String chequeNo;

    @Column(name = "voucher_no")
    private String voucherNo;

    private String note;

    @Enumerated(EnumType.STRING)
    private ExpenseStatus status;

    @Column(columnDefinition = "LONGTEXT")
    private String image;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private ExpenseSubcategory expenseSubcategory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
