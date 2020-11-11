package com.business.manager.horario.model.streams;

import com.business.manager.horario.enums.StreamAction;
import com.business.manager.horario.model.ubicacion.UbicacionModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class UbicacionStreamModel {
    private StreamAction action;
    private UbicacionModel ubicacion;
}
