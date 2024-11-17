package org.POS.backend.user_log;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.POS.backend.user.User;

import java.time.LocalDate;

@Entity
@Table(name = "user_logs")
@Getter
@Setter
@NoArgsConstructor
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    private LocalDate date;

    private String action;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
