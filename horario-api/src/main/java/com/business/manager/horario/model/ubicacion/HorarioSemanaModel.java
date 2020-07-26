package com.business.manager.horario.model.ubicacion;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HorarioSemanaModel {
	private HorarioUbicacionModel lunes;
	private HorarioUbicacionModel martes;
	private HorarioUbicacionModel miercoles;
	private HorarioUbicacionModel jueves;
	private HorarioUbicacionModel viernes;
	private HorarioUbicacionModel sabado;
	private HorarioUbicacionModel domingo;
}
