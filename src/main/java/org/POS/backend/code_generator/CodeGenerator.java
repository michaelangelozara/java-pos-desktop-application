package org.POS.backend.code_generator;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "code_generator")
@Data
@NoArgsConstructor
public class CodeGenerator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "last_count")
    private int lastCount;
}
