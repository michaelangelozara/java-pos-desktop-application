package org.POS.backend.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.POS.backend.cash_transaction.CashTransaction;
import org.POS.backend.expense.Expense;
import org.POS.backend.inventory_adjustment.InventoryAdjustment;
import org.POS.backend.open_cash.OpenCash;
import org.POS.backend.purchase.Purchase;
import org.POS.backend.quotation.Quotation;
import org.POS.backend.return_product.ReturnProduct;
import org.POS.backend.sale.Sale;
import org.POS.backend.stock.Stock;
import org.POS.backend.user_log.UserLog;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CashTransaction> cashTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpenCash> openCashes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchase> purchases = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryAdjustment> inventoryAdjustments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quotation> quotations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReturnProduct> returnProducts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLog> userLogs = new ArrayList<>();

    public void addUserLog(UserLog userLog){
        userLogs.add(userLog);
        userLog.setUser(this);
    }

    public void addReturnProduct(ReturnProduct returnProduct){
        returnProducts.add(returnProduct);
        returnProduct.setUser(this);
    }

    public void addQuotation(Quotation quotation){
        quotations.add(quotation);
        quotation.setUser(this);
    }

    public void addInventoryAdjustment(InventoryAdjustment adjustment){
        inventoryAdjustments.add(adjustment);
        adjustment.setUser(this);
    }

    public void addPurchase(Purchase purchase){
        purchases.add(purchase);
        purchase.setUser(this);
    }

    public void addOpenCash(OpenCash openCash){
        openCashes.add(openCash);
        openCash.setUser(this);
    }

    public void addCashTransaction(CashTransaction cashTransaction){
        cashTransactions.add(cashTransaction);
        cashTransaction.setUser(this);
    }

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
