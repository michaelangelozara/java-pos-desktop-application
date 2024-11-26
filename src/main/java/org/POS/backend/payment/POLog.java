package org.POS.backend.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "po_logs")
@Getter
@Setter
@NoArgsConstructor
public class POLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "amount_due")
    private BigDecimal recentAmountDue;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
