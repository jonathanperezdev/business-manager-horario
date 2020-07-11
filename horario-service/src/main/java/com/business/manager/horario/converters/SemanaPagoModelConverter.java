package com.business.manager.horario.converters;

import com.business.manager.horario.dao.entities.HorarioUbicacion;
import com.business.manager.horario.dao.entities.SemanaPago;
import com.business.manager.horario.enums.DiaEnum;
import com.business.manager.horario.model.HorarioSemanaModel;
import com.business.manager.horario.model.HorarioUbicacionModel;
import com.business.manager.horario.model.SemanaPagoModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SemanaPagoModelConverter implements Converter<SemanaPago, SemanaPagoModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SemanaPagoModel convert(SemanaPago semanaPago) {
        SemanaPagoModel semana  = modelMapper.map(semanaPago, SemanaPagoModel.class);
        return semana;
    }
}
