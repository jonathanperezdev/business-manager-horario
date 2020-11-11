package com.business.manager.horario.services;

import com.business.manager.horario.enums.ComponentEnum;
import com.business.manager.horario.model.ParametroModel;
import com.business.manager.horario.model.streams.ParametroStreamModel;

import java.util.List;

public interface ParametroService {
    List<ParametroModel> findAllParametro();

    ParametroModel createParametro(ParametroModel parametro);

    ParametroModel updateParametro(ParametroModel parametroModel);

    void deleteParametro(Integer idParametro);

    String getValueOfParametro(String parametro);
    void sendAllParametros();

    ComponentEnum[] getAllComponentes();
}
