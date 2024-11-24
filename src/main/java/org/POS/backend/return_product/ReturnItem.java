package org.POS.backend.return_product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.sale_product.SaleProduct;

import java.time.LocalDate;

@Entity
@Table(name = "return_items")
@Getter
@Setter
@NoArgsConstructor
public class ReturnItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "returned_quantity")
    private int returnedQuantity;

    @Column(name = "returned_date")
    private LocalDate returnedDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sale_product_id")
    private SaleProduct saleProduct;

    @ManyToOne
    @JoinColumn(name = "return_order_id")
    private ReturnOrder returnOrder;
}
