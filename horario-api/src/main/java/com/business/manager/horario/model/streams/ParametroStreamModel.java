package com.business.manager.horario.model.streams;

import com.business.manager.horario.enums.StreamAction;
import com.business.manager.horario.model.ParametroModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ParametroStreamModel {
    private StreamAction action;
    private ParametroModel parametro;
}
