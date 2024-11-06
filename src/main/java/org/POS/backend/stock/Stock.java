package org.POS.backend.stock;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.person.Person;

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

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private StockType type;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
