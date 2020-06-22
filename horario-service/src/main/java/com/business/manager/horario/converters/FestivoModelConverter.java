package com.business.manager.horario.converters;

import com.business.manager.horario.dao.entities.Festivo;
import com.business.manager.horario.model.FestivoModel;
import com.business.manager.horario.util.DateUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FestivoModelConverter implements Converter<Festivo, FestivoModel> {

    @Override
    public FestivoModel convert(Festivo festivo) {
        FestivoModel festivoModel = new FestivoModel();

        festivoModel.setId(festivo.getId());
        festivoModel.setFestivo(DateUtil.getStringOf(festivo.getFestivo()));

        return festivoModel;
    }
}


