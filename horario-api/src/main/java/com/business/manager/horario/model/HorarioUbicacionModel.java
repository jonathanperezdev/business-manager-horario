package com.business.manager.horario.model;

import com.business.manager.horario.serializers.TemporalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.temporal.Temporal;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class HorarioUbicacionModel {
	private Integer id;
	
	@NonNull
	private String dia;

	@NonNull
	@JsonSerialize(using = TemporalSerializer.class)
	private Temporal fechaInicio;

	@NonNull
	@JsonSerialize(using = TemporalSerializer.class)
	private Temporal fechaFin;
	
	private String fechas;	
	private Double horas;
}
