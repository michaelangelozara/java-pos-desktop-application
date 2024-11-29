package org.POS.backend.stock;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.person.Person;
import org.POS.backend.product.Product;
import org.POS.backend.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "stocks")
@Getter
@Setter
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    @Column(name = "stock_in_or_out")
    private int stockInOrOut;

    @Column(name = "recent_quantity")
    private int recentQuantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private StockType type;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Person person;
}
