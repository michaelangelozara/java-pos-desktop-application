package org.POS.backend.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.sale.Sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    @Column(name = "reference_number", columnDefinition = "VARCHAR(50) NOT NULL")
    private String referenceNumber;

    @Column(precision = 10, scale = 2, name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(precision = 10, scale = 2, name = "amount_due")
    private BigDecimal amountDue;

    @Column(name = "change_amount", precision = 10, scale = 2)
    private BigDecimal changeAmount;

    private BigDecimal discount;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "discount_type")
    @Enumerated(EnumType.STRING)
    private PaymentDiscountType discountType;

    @OneToOne(mappedBy = "payment")
    private Sale sale;

    @OneToMany(mappedBy = "payment", cascade = {CascadeType.MERGE})
    private List<POLog> poLogs = new ArrayList<>();

    public void addPOLog(POLog poLog){
        poLogs.add(poLog);
        poLog.setPayment(this);
    }
}
