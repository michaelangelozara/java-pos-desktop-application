package org.POS.backend.return_purchase;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.purchase.Purchase;
import org.POS.backend.purchased_item.PurchaseItem;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "return_purchases")
@Getter
@Setter
@NoArgsConstructor
public class ReturnPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String reason;

    @Enumerated(EnumType.STRING)
    private ReturnPurchaseStatus status;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    private String note;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @ManyToMany
    @JoinTable(
            name = "return_purchase_purchase_item",
            joinColumns = {@JoinColumn(name = "return_purchase_id")},
            inverseJoinColumns = {@JoinColumn(name = "purchase_item_id")}
    )
    private List<PurchaseItem> purchaseItems = new ArrayList<>();

    public void addPurchaseItem(PurchaseItem purchaseItem){
        purchaseItems.add(purchaseItem);
    }
}
