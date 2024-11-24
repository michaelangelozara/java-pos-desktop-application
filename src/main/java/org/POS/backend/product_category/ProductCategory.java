package org.POS.backend.product_category;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.POS.backend.product.Product;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product_categories")
@Data
@NoArgsConstructor
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProductCategoryStatus status;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    private String note;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @OneToMany(mappedBy = "productCategory")
    private Set<Product> products = new HashSet<>();

    public void addProduct(Product product){
        products.add(product);
        product.setProductCategory(this);
    }
}
