package org.POS.backend.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.invoice.Invoice;
import org.POS.backend.person.Person;
import org.POS.backend.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    @Column(precision = 10, scale = 2, name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(precision = 10, scale = 2, name = "amount_due")
    private BigDecimal amountDue;

    @Column(precision = 10, scale = 2, name = "net_total")
    private BigDecimal netTotal;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
