package org.POS.backend.purchased_item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.purchase.Purchase;

import java.time.LocalDate;

@Entity
@Table(name = "purchase_items")
@Getter
@Setter
@NoArgsConstructor
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    private int quantity;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    private String name;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;
}
