package org.POS.backend.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.POS.backend.department.Department;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String designation;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "contact_number")
    private String contactNumber;

    private BigDecimal salary;

    private int commission;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Enumerated(EnumType.STRING)
    private UserReligion religion;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "join_date")
    private LocalDate joinDate;

    private String address;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @Column(columnDefinition = "TEXT")
    private String profilePicture;

    @Column(columnDefinition = "VARCHAR(50)", unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
