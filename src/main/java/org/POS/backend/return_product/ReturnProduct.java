package org.POS.backend.return_product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.order.Order;
import org.POS.backend.sale_item.SaleItem;
import org.POS.backend.user.User;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(mappedBy = "returnProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleItem> returnedSaleItems = new ArrayList<>();

    public void addReturnSaleItem(SaleItem saleItem){
        returnedSaleItems.add(saleItem);
        saleItem.setReturnProduct(this);
    }
}