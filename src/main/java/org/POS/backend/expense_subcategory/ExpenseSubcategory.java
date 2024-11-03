package org.POS.backend.expense_subcategory;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.POS.backend.expense.Expense;
import org.POS.backend.expense_category.ExpenseCategory;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "expense_subcategories")
@Data
@NoArgsConstructor
public class ExpenseSubcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String note;

    @Enumerated(EnumType.STRING)
    private ExpenseSubcategoryStatus status;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @ManyToOne
    @JoinColumn(name = "expense_category_id")
    private ExpenseCategory expenseCategory;

    @OneToMany(mappedBy = "expenseSubcategory")
    private List<Expense> expenses;
}
