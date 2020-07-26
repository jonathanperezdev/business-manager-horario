package com.business.manager.horario.exceptions.errors;

public enum ErrorEnum {
	//HorarioUbicacion
	UBICACION_NOT_FOUND("No existe la ubicacion con codigo %d"),
	UBICACIONES_NOT_FOUND("No existen ubicaciones creadas"),

	//Parametros
	PARAMETRO_ALREADY_EXIST("El parametro %s ya existe"),
	PARAMETRO_NOT_FOUND("El parametro %s, no existe en la base de datos"),
	PARAMETROS_NOT_FOUND("No existen parametros creados"),

	//Festivos
	FESTIVO_ALREADY_EXIST("El festivo con fecha %s ya existe"),
	FESTIVO_NOT_FOUND_BY_YEAR("No existen festivos para el año %d "),
	FESTIVO_NO_CREADOS("No estan definidos los festivos para el año %d"),
	NUMERO_FESTIVOS_BAJO("El numero de festivos para el año %d deberia ser mayor a %d"),

	//Periodo de pago
	PERIODO_PAGO_EXISTENTE("Ya existe un periodo de pago creado entre las fechas %s - %s"),
	PERIODO_PAGO_NO_PERMITIDO("No debe existir mas de %d dias para un periodo de pago"),
	PERIODO_PAGO_FECHA_INICIO_SUPERIOR("La fecha de inicio %s no puede ser mayor a la fecha final %s"),
	PERIODO_PAGO_NOT_FOUND_BY_YEAR("No existen periodos de pago para el año %d "),
	PERIODO_PAGO_LIQUIDADO("No puede eliminar un periodo de pago que ya fue liquidado"),
	PERIODO_PAGO_NOT_FOUND("No se encontro el periodo de pago con el id %d"),
	PERIODO_PAGO_HORARIO_EMPLEADO_NOT_FOUND("No existe horario para la semana %d y ubicacion %d")
	;
	
	private String message;
	
	private ErrorEnum(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

}
