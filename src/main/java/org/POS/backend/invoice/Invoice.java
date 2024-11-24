package org.POS.backend.invoice;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.sale.Sale;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "invoice_number", columnDefinition = "VARCHAR(50) NOT NULL")
    private String invoiceNumber;

    @OneToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;
}
