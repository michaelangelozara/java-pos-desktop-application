package org.POS.backend.quoted_item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.product.Product;
import org.POS.backend.product.ProductType;
import org.POS.backend.product_attribute.ProductVariation;
import org.POS.backend.quotation.Quotation;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "quoted_items")
@Getter
@Setter
@NoArgsConstructor
public class QuotedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int quantity;

    @Column(name = "purchase_price", precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "selling_price", precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "quoted_item_type")
    private QuotedItemType quotedItemType;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "variation_id")
    private ProductVariation variation;

    @ManyToOne
    @JoinColumn(name = "quotation_id")
    private Quotation quotation;
}
