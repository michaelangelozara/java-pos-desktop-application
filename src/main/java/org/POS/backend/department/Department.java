package org.POS.backend.department;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.POS.backend.user.User;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private DepartmentStatus status;

    private String note;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @OneToMany(mappedBy = "department")
    private List<User> users;
}
