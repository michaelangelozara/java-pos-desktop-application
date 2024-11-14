package org.POS.backend.inventory_adjustment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.product.Product;
import org.POS.backend.user.User;

import java.time.LocalDate;

@Entity
@Table(name = "inventory_adjustments")
@Getter
@Setter
@NoArgsConstructor
public class InventoryAdjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String reason;

    private String note;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    private int quantity;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    @Enumerated(EnumType.STRING)
    private InventoryAdjustmentType type;

    @Enumerated(EnumType.STRING)
    private InventoryAdjustmentStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
