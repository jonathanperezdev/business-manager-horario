package com.business.manager.horario.services;

import com.business.manager.horario.model.ParametroModel;

public interface ParametroService {
    ParametroModel createParametro(ParametroModel parametro);

    String getValueOfParametro(String parametro);
}
