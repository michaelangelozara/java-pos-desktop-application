package org.POS.backend.return_product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.sale_item.SaleItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "return_reason")
    private String returnReason;

    @Column(name = "cost_of_return_products", precision = 10, scale = 2)
    private BigDecimal costOfReturnProducts;

    @Column(name = "returned_at")
    private LocalDate returnedAt;

    @OneToMany(mappedBy = "returnProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleItem> returnedSaleItem = new ArrayList<>();

    public void addReturnSaleItem(SaleItem saleItem){
        returnedSaleItem.add(saleItem);
        saleItem.setReturnProduct(this);
    }
}