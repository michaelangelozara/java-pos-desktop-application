package org.POS.backend.return_product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "return_products")
@Getter
@Setter
@NoArgsConstructor
public class ReturnProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    @Column(name = "purchase_code", columnDefinition = "VARCHAR(50) NOT NULL")
    private String purchaseCode;

    @Column(name = "return_reason")
    private String returnReason;

    @Column(name = "cost_of_return_products", precision = 10, scale = 2)
    private BigDecimal costOfReturnProducts;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private ReturnStatus status;
}