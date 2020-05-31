package com.business.manager.horario.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;

public class DateUtil {
	private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
	
	public static String getStringOf(LocalDate fecha) {
		return dateFormat.format(fecha);
	}
	
	public static String getStringOf(LocalDateTime fecha) {
		return dateTimeFormat.format(fecha);
	}
	
	public static String getStringOf(LocalTime time) {
		return timeFormat.format(time);
	}
	
	public static LocalTime getLocalTimeOf(String time) {
		return LocalTime.parse(time,timeFormat);
	}
	
	public static LocalDate getLocalDateOf(String fecha) {
		return LocalDate.parse(fecha);
	}
	
	public static LocalDateTime getLocalDateTimeOf(String fecha) {
		return LocalDateTime.parse(fecha);
	}
	
	public static Temporal getTemporalOf(String fecha) {
		Temporal fechaTemporal;
		try {
			fechaTemporal = getLocalTimeOf(fecha);
		} catch(DateTimeParseException ex) {
			try {
				fechaTemporal = getLocalDateOf(fecha);
			}catch(DateTimeParseException ex2) {
				fechaTemporal = getLocalDateTimeOf(fecha);
			}
		}
		
		return fechaTemporal;
	}
	
	public static String getStringOf(Temporal fecha) {
		String fechaString ="";
	    if(fecha instanceof LocalTime) {
	    	fechaString = getStringOf((LocalTime)fecha);
	    }else if(fecha instanceof LocalDate) {
	    	fechaString = getStringOf((LocalDate)fecha);
	    }else if(fecha instanceof LocalDateTime) {
	    	fechaString = getStringOf((LocalDateTime)fecha);
	    }
		
		return fechaString;
	}
}
	