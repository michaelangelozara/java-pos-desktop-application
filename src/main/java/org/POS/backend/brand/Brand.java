package org.POS.backend.brand;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.POS.backend.product.Product;
import org.POS.backend.product_subcategory.ProductSubcategory;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "brands")
@Data
@NoArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private boolean isDeleted;

    private LocalDate deletedAt;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    @Enumerated(EnumType.STRING)
    private BrandStatus status;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private ProductSubcategory productSubcategory;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;
}
