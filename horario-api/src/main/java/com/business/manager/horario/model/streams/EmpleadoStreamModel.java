package com.business.manager.horario.model.streams;

import com.business.manager.horario.enums.StreamAction;
import com.business.manager.horario.model.empleado.EmpleadoModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class EmpleadoStreamModel {
    private StreamAction action;
    private EmpleadoModel empleado;
}
