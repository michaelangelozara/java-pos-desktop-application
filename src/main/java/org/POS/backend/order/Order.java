package org.POS.backend.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.return_product.ReturnOrder;
import org.POS.backend.sale.Sale;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_number", columnDefinition = "VARCHAR(50) NOT NULL")
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    private List<ReturnOrder> returnOrders = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

}
