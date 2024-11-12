package org.POS.backend.cash_transaction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.sale.Sale;
import org.POS.backend.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cash_transactions")
@Getter
@Setter
@NoArgsConstructor
public class CashTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    @Column(columnDefinition = "VARCHAR(300) NOT NULL")
    private String reference;

    @Column(name = "cash_in", precision = 10, scale = 2)
    private BigDecimal cashIn;

    @Column(name = "cash_out", precision = 10, scale = 2)
    private BigDecimal cashOut;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private CashTransactionPaymentMethod cashTransactionPaymentMethod;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @OneToOne(mappedBy = "cashTransaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}