package org.POS.backend.sale_item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.product.Product;
import org.POS.backend.sale.Sale;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_items")
@Getter
@Setter
@NoArgsConstructor
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int quantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
