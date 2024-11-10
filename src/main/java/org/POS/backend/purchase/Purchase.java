package org.POS.backend.purchase;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.person.Person;
import org.POS.backend.purchased_item.PurchaseItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter

@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "po_reference")
    private String poReference;

    @Column(name = "payment_term")
    private String paymentTerm;

    @Column(name = "purchase_tax")
    private int purchaseTax;

    private String note;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "purchase_order_date")
    private LocalDate purchaseOrderDate;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(precision = 10, scale = 2)
    private BigDecimal balance;

    @Column(name = "subtotal_tax", precision = 10, scale = 2)
    private BigDecimal subtotalTax;

    @Column(name = "total_tax", precision = 10, scale = 2)
    private BigDecimal totalTax;

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(name = "transport_cost", precision = 10, scale = 2)
    private BigDecimal transportCost;

    @Column(name = "net_total", precision = 10, scale = 2)
    private BigDecimal netTotal;

    @Column(name = "total_paid", precision = 10, scale = 2)
    private BigDecimal totalPaid;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @Column(name = "receipt_number")
    private String receiptNumber;

    private String account;

    @Column(name = "cheque_number")
    private String chequeNumber;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PurchaseItem> purchaseItems = new ArrayList<>();

    public void addPurchaseItem(PurchaseItem purchaseItem) {
        purchaseItems.add(purchaseItem);
        purchaseItem.setPurchase(this);
    }
}
