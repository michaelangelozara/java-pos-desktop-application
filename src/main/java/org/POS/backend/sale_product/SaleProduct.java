package org.POS.backend.sale_product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.product.Product;
import org.POS.backend.product_attribute.ProductVariation;
import org.POS.backend.sale.Sale;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_products")
@Getter
@Setter
@NoArgsConstructor
public class SaleProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int quantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "variation_id")
    private ProductVariation productVariation;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

}
