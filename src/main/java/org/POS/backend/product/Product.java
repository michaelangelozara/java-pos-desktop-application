package org.POS.backend.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.POS.backend.brand.Brand;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String model;

    private String code;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @Enumerated(EnumType.STRING)
    private ProductUnit unit;

    @Column(name = "product_tax")
    private int productTax;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_type")
    private ProductTaxType taxType;

    @Column(name = "regular_price")
    private BigDecimal regularPrice;

    private int discount;

    private String note;

    private int stock;

    @Column(name = "alert_quantity")
    private int alertQuantity;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(columnDefinition = "TEXT")
    private String image;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
