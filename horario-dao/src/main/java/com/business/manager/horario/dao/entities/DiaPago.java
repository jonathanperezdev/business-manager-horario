package com.business.manager.horario.dao.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "DIA_PAGO")
public class DiaPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idSemana;

    @NonNull
    private Integer idUbicacion;

    @NonNull
    private LocalDateTime fechaInicio;

    @NonNull
    private LocalDateTime fechaFin;

    @NonNull
    private Long idEmpleado;

    @OneToMany(
            mappedBy = "diaPago",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private Set<Recargo> recargos = new HashSet();
}
