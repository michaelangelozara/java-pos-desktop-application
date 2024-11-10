package org.POS.backend.purchased_item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.product.Product;
import org.POS.backend.purchase.Purchase;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private boolean isDelete;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

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
}