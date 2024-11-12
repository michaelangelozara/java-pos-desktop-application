package org.POS.backend.open_cash;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "open_cashes")
@Getter
@Setter
@NoArgsConstructor
public class OpenCash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(precision = 10, scale = 2)
    private BigDecimal cash;

    private String note;

    @Enumerated(EnumType.STRING)
    private OpenCashType type;

    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
