package com.business.manager.horario.dao.entities;

import com.business.manager.horario.enums.EstadoPago;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @OrderBy(value = "numero_semana")
    private List<SemanaPago> semanasPago = new ArrayList<>();
}

