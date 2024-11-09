package org.POS.backend.sale;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.person.Person;
import org.POS.backend.product.Product;
import org.POS.backend.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sales")
@NoArgsConstructor
@Getter
@Setter
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int quantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    private String discountType;

    @Column(precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(name = "transport_cost", precision = 10, scale = 2)
    private BigDecimal transportCost;

    @Column(name = "total_tax", precision = 10, scale = 2)
    private BigDecimal totalTax;

    @Column(name = "net_total", precision = 10, scale = 2)
    private BigDecimal netTotal;

    @Column(name = "receipt_number")
    private String receiptNumber;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "cheque_number")
    private String chequeNumber;

    @Column(name = "po_reference")
    private String poReference;

    private String payment;

    @Column(name = "date_of_transaction")
    private LocalDate dateOfTransaction;

    @Column(name = "delivery_place")
    private String deliveryPlace;

    private String note;

    @ManyToMany
    @JoinTable(
            name = "sales_products",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    public void addProduct(Product product){
        products.add(product);
        product.getSales().add(this);
    }

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}