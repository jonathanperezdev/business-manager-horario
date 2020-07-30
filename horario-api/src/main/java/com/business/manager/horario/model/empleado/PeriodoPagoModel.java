package com.business.manager.horario.model.empleado;

import com.business.manager.horario.serializers.TemporalDeserializer;
import com.business.manager.horario.serializers.TemporalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.temporal.Temporal;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Data
@NoArgsConstructor
public class PeriodoPagoModel {
    private Long id;
    private Long diasLiquidados;

    @NonNull
    @JsonDeserialize(using = TemporalDeserializer.class)
    @JsonSerialize(using = TemporalSerializer.class)
    private Temporal fechaInicio;

    @NonNull
    @JsonDeserialize(using = TemporalDeserializer.class)
    @JsonSerialize(using = TemporalSerializer.class)
    private Temporal fechaFin;

    Set<SemanaPagoModel> semanas;
}
