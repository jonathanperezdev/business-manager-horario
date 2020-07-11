package com.business.manager.horario.model;

import com.business.manager.horario.serializers.TemporalDeserializer;
import com.business.manager.horario.serializers.TemporalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.temporal.Temporal;

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
