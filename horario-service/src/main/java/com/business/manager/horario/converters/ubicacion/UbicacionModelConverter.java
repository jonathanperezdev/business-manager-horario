package com.business.manager.horario.converters.ubicacion;

import com.business.manager.horario.converters.ubicacion.HorarioSemanaModelConverter;
import com.business.manager.horario.dao.entities.Ubicacion;
import com.business.manager.horario.model.ubicacion.UbicacionModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UbicacionModelConverter implements Converter<Ubicacion, UbicacionModel> {

    @Autowired
    private HorarioSemanaModelConverter semanaModelConverter;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UbicacionModel convert(Ubicacion ubicacion) {
        UbicacionModel model = modelMapper.map(ubicacion, UbicacionModel.class);
        model.setHorarioSemana(semanaModelConverter.convert(ubicacion.getHorariosByUbicacion()));

        return model;
    }
}
