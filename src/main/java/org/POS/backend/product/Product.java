package org.POS.backend.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.brand.Brand;
import org.POS.backend.product_subcategory.ProductSubcategory;
import org.POS.backend.purchased_product.PurchaseProduct;
import org.POS.backend.sale_item.SaleItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private List<SaleItem> saleItems = new ArrayList<>();

    public void addSaleItem(SaleItem saleItem){
        saleItems.add(saleItem);
        saleItem.setProduct(this);
    }
}
