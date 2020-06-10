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
            targetEntity= DiaPago.class,
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "idSemana", referencedColumnName = "id")
    private Set<DiaPago> diasPago = new HashSet();
}
