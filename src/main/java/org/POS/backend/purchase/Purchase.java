package org.POS.backend.purchase;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.person.Person;
import org.POS.backend.purchased_product.PurchaseProduct;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table@Getter
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

    @Column(name = "total_due", precision = 10, scale = 2)
    private BigDecimal totalDue;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "purchase")
    private Set<PurchaseProduct> purchaseProducts;
}
