package org.POS.backend.product_attribute;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.sale_product.SaleProduct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_variations")
@Getter
@Setter
@NoArgsConstructor
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String variation;

    private int quantity;

    @Column(name = "purchase_price", precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "suggested_retail_price", precision = 10, scale = 2)
    private BigDecimal srp;

    @ManyToOne
    @JoinColumn(name = "product_attribute_id")
    private ProductAttribute productAttribute;

    @OneToMany(mappedBy = "productVariation")
    private List<SaleProduct> saleProducts = new ArrayList<>();

    public void addSaleProduct(SaleProduct saleProduct){
        saleProducts.add(saleProduct);
        saleProduct.setProductVariation(this);
    }
}
