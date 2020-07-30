package com.business.manager.horario.model.ubicacion;

import com.business.manager.horario.model.ubicacion.HorarioSemanaModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UbicacionModel {
	
	private Integer id;
	
	@NonNull
	private String nombre;
	
	@NonNull
    HorarioSemanaModel horarioSemana;
}
