package org.POS.backend.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.brand.Brand;
import org.POS.backend.product_subcategory.ProductSubcategory;
import org.POS.backend.purchased_product.PurchaseProduct;
import org.POS.backend.sale.Sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String model;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @Enumerated(EnumType.STRING)
    private ProductUnit unit;

    @Column(name = "product_tax")
    private int productTax;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_type")
    private ProductTaxType taxType;

    @Column(name = "purchase_price", precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "selling_price", precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    @Column(name = "percentage_discount")
    private int percentageDiscount;

    private String note;

    private int stock;

    @Column(name = "alert_quantity")
    private int alertQuantity;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(columnDefinition = "LONGTEXT")
    private String image;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "product_subcategory_id")
    private ProductSubcategory productSubcategory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseProduct> purchaseProducts = new ArrayList<>();

    public void addPurchaseProduct(PurchaseProduct purchaseProduct){
        purchaseProducts.add(purchaseProduct);
        purchaseProduct.setProduct(this);
    }

    @ManyToMany(mappedBy = "products")
    private Set<Sale> sales = new HashSet<>();

    public void addSale(Sale sale){
        sales.add(sale);
        sale.getProducts().add(this);
    }
}
