package org.POS.backend.sale;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.cash_transaction.CashTransaction;
import org.POS.backend.person.Person;
import org.POS.backend.sale_item.SaleItem;
import org.POS.backend.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sales")
@NoArgsConstructor
@Getter
@Setter
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String discountType;

    @Column(precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(name = "transport_cost", precision = 10, scale = 2)
    private BigDecimal transportCost;

    @Column(name = "total_tax", precision = 10, scale = 2)
    private BigDecimal totalTax;

    @Column(name = "net_total", precision = 10, scale = 2)
    private BigDecimal netTotal;

    @Column(name = "receipt_number", columnDefinition = "VARCHAR(50) NOT NULL")
    private String receiptNumber;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "amount_due", precision = 10, scale = 2)
    private BigDecimal amountDue;

    @Column(name = "po_reference")
    private String poReference;

    private String payment;

    @Column(name = "delivery_place")
    private String deliveryPlace;

    private String note;

    private LocalDate date;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    @Column(name = "vat_sales", precision = 10, scale = 2)
    private BigDecimal vatSales;

    @OneToOne
    @JoinColumn(name = "cash_transaction_id")
    private CashTransaction cashTransaction;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SaleItem> saleItems = new HashSet<>();

    public void addSaleItem(SaleItem saleItem){
        saleItems.add(saleItem);
        saleItem.setSale(this);
    }
}