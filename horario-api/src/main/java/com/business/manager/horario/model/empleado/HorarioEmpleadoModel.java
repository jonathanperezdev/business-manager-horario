package com.business.manager.horario.model.empleado;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class HorarioEmpleadoModel {

	@NonNull
	private EmpleadoModel empleado;

	private DiaPagoModel lunes;
	private DiaPagoModel martes;
	private DiaPagoModel miercoles;
	private DiaPagoModel jueves;
	private DiaPagoModel viernes;
	private DiaPagoModel sabado;
	private DiaPagoModel domingo;
}
