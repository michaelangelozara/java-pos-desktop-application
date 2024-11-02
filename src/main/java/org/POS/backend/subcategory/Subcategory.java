package org.POS.backend.subcategory;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.POS.backend.brand.Brand;
import org.POS.backend.category.Category;
import org.POS.backend.expense.Expense;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "subcategories")
@Data
@NoArgsConstructor
public class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private SubcategoryStatus status;

    private String code;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    private String note;

    @OneToMany(mappedBy = "subcategory")
    private List<Brand> brands;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "subcategory")
    private List<Expense> expenses;
}

