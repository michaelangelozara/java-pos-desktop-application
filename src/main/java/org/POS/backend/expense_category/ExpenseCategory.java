package org.POS.backend.expense_category;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.POS.backend.expense_subcategory.ExpenseSubcategory;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "expense_categories")
@Data
@NoArgsConstructor
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ExpenseCategoryStatus status;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    private String note;

    @OneToMany(mappedBy = "expenseCategory")
    private List<ExpenseSubcategory> expenseSubcategories;
}
