package com.business.manager.horario.dao.entities;

import com.business.manager.horario.enums.EstadoPago;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PERIODO_PAGO")
public class PeriodoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private LocalDate fechaInicio;

    @NonNull
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago = EstadoPago.PENDIENTE;

    @OneToMany(
            mappedBy = "periodo",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private Set<SemanaPago> semanasPago = new HashSet();
}

