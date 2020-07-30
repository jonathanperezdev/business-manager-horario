package com.business.manager.horario.model.empleado;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.temporal.Temporal;
import java.util.List;

@Data
@NoArgsConstructor
public class SemanaPagoModel {
    private Long id;

    @NonNull
    private Short numeroSemana;

    @NonNull
    private Temporal fechaInicio;

    @NonNull
    private Temporal fechaFin;
}
