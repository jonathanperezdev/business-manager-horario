package com.business.manager.horario.dao.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

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
}
