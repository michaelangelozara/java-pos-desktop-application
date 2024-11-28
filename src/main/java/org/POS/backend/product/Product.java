package org.POS.backend.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.inventory_adjustment.InventoryAdjustment;
import org.POS.backend.product_attribute.ProductAttribute;
import org.POS.backend.product_category.ProductCategory;
import org.POS.backend.purchased_item.PurchaseItem;
import org.POS.backend.quoted_item.QuotedItem;
import org.POS.backend.sale_product.SaleProduct;
import org.POS.backend.stock.Stock;

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

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @Enumerated(EnumType.STRING)
    private ProductUnit unit;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String productCode;

    @Column(name = "purchase_price", precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "selling_price", precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    private String note;

    private int stock;

    private LocalDate date;

    @Column(name = "alert_quantity")
    private int alertQuantity;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(columnDefinition = "LONGTEXT")
    private String image;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Stock> stocks = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryAdjustment> inventoryAdjustments = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleProduct> saleProducts = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuotedItem> quotedItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ProductAttribute> productAttributes = new ArrayList<>();

    public void addProductAttribute(ProductAttribute productAttribute){
        productAttributes.add(productAttribute);
        productAttribute.setProduct(this);
    }

    public void addQuotedItem(QuotedItem quotedItem){
        quotedItems.add(quotedItem);
        quotedItem.setProduct(this);
    }

    public void addSaleItem(SaleProduct saleProduct){
        saleProducts.add(saleProduct);
        saleProduct.setProduct(this);
    }

    public void addInventoryAdjustment(InventoryAdjustment adjustment){
        inventoryAdjustments.add(adjustment);
        adjustment.setProduct(this);
    }

    public void addStock(Stock stock){
        stocks.add(stock);
        stock.setProduct(this);
    }
}
