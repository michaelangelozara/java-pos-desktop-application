package org.POS.backend.purchased_item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.product.Product;
import org.POS.backend.purchase.Purchase;
import org.POS.backend.return_purchase.ReturnPurchase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_items")
@Getter
@Setter
@NoArgsConstructor
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int quantity;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @Column(name = "is_returned")
    private boolean isReturned;

    @Column(name = "returned_at")
    private LocalDate returnedAt;

    @Column(name = "return_quantity")
    private int returnQuantity;

    @Column(name = "return_price")
    private BigDecimal returnPrice;

    @Column(name = "purchase_price", precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "selling_price", precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal tax;

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    private String productCode;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToMany(mappedBy = "purchaseItems")
    private List<ReturnPurchase> returnPurchases = new ArrayList<>();

    public void addReturnPurchase(ReturnPurchase returnPurchase){
        returnPurchases.add(returnPurchase);
    }
}
