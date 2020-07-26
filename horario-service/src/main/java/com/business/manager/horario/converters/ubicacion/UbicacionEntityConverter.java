package com.business.manager.horario.converters.ubicacion;

import com.business.manager.horario.converters.ubicacion.HorarioSemanaEntityConverter;
import com.business.manager.horario.dao.entities.HorarioUbicacion;
import com.business.manager.horario.dao.entities.Ubicacion;
import com.business.manager.horario.model.ubicacion.UbicacionModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component
public class UbicacionEntityConverter implements Converter<UbicacionModel, Ubicacion> {

    @Autowired
    private HorarioSemanaEntityConverter semanaEntityConverter;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Ubicacion convert(UbicacionModel ubicacionModel) {
        Ubicacion ubicacion = modelMapper.map(ubicacionModel, Ubicacion.class);

        Set<HorarioUbicacion> HorarioByUbicacion = semanaEntityConverter.convert(ubicacionModel.getHorarioSemana());
        HorarioByUbicacion.stream()
                .filter(Objects::nonNull)
                .forEach(horario -> horario.setUbicacion(ubicacion));

        ubicacion.setHorariosByUbicacion(HorarioByUbicacion);

        return ubicacion;
    }
}
