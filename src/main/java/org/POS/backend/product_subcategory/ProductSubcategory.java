package org.POS.backend.product_subcategory;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.POS.backend.brand.Brand;
import org.POS.backend.product.Product;
import org.POS.backend.product_category.ProductCategory;
import org.POS.backend.expense.Expense;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "product_subcategories")
@Data
@NoArgsConstructor
public class ProductSubcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProductSubcategoryStatus status;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    private String note;

    @OneToMany(mappedBy = "productSubcategory")
    private List<Brand> brands;

    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "productSubcategory")
    private List<Product> products;
}

