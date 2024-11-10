package org.POS.backend.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.POS.backend.expense.Expense;
import org.POS.backend.sale.Sale;
import org.POS.backend.stock.Stock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "contact_number")
    private String contactNumber;

    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @Column(columnDefinition = "VARCHAR(50)", unique = true)
    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sale> sales = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stock> stocks = new ArrayList<>();

    public void addStock(Stock stock){
        stocks.add(stock);
        stock.setUser(this);
    }

    public void addExpense(Expense expense){
        expenses.add(expense);
        expense.setUser(this);
    }

    public void addSale(Sale sale){
        sales.add(sale);
        sale.setUser(this);
    }
}
