package com.business.manager.horario.enums;

import java.time.LocalDate;
import java.util.Arrays;

public enum DiaEnum {
	MONDAY((short)1,"Lunes"),
	TUESDAY((short)2,"Martes"),
	WEDNESDAY((short)3,"Miercoles"),
	THURSDAY((short)4,"Jueves"),
	FRIDAY((short)5,"Viernes"),
	SATURDAY((short)6,"Sabado"),
	SUNDAY((short)7,"Domingo");
	
	private Short orden;
	private String dia;
	
	DiaEnum(Short orden, String dia){
		this.orden = orden;
		this.dia = dia;
	}
	
	public static DiaEnum diaEnumOf(LocalDate fecha) {
		return DiaEnum.valueOf(fecha.getDayOfWeek().name());
	}

	public static DiaEnum diaEnumOf(String dia) {
		return Arrays.stream(DiaEnum.values())
				.filter(e -> e.dia.equals(dia))
				.findFirst().get();
	}

	public Short getOrden() {
		return orden;
	}	

	public String getDia() {
		return dia;
	}	
}
