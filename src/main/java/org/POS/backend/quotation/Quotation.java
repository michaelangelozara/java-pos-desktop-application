package org.POS.backend.quotation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.person.Person;
import org.POS.backend.quoted_item.QuotedItem;
import org.POS.backend.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quotations")
@Getter
@Setter
@NoArgsConstructor
public class Quotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "purchase_tax")
    private int purchaseTax;

    private String note;
    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "subtotal_tax", precision = 10, scale = 2)
    private BigDecimal subtotalTax;

    @Column(name = "total_tax", precision = 10, scale = 2)
    private BigDecimal totalTax;

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(precision = 10, scale = 2)
    private BigDecimal discount;

    @Enumerated(EnumType.STRING)
    private QuotationStatus status;

    @Column(name = "transport_cost", precision = 10, scale = 2)
    private BigDecimal transportCost;

    @Column(name = "net_total", precision = 10, scale = 2)
    private BigDecimal netTotal;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @OneToMany(mappedBy = "quotation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuotedItem> quotedItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public void addQuotedItem(QuotedItem quotedItem){
        quotedItems.add(quotedItem);
        quotedItem.setQuotation(this);
    }
}
