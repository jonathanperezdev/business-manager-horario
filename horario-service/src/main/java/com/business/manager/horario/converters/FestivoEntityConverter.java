package com.business.manager.horario.converters;

import com.business.manager.horario.dao.entities.Festivo;
import com.business.manager.horario.model.FestivoModel;
import com.business.manager.horario.util.DateUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FestivoEntityConverter implements Converter<FestivoModel, Festivo> {

    @Override
    public Festivo convert(FestivoModel festivoModel) {
        Festivo festivo = new Festivo();

        festivo.setId(festivoModel.getId());
        festivo.setFestivo(DateUtil.getLocalDateOf(festivoModel.getFestivo()));

        return festivo;
    }
}


