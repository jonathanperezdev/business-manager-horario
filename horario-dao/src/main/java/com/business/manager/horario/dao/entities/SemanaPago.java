package com.business.manager.horario.dao.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SEMANA_PAGO")
public class SemanaPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Short numeroSemana;

    @ManyToOne
    @JoinColumn(name = "id_periodo")
    private PeriodoPago periodo;

    @OneToMany(
            mappedBy = "semanaPago",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private Set<DiaPago> diasPago = new HashSet();
}
