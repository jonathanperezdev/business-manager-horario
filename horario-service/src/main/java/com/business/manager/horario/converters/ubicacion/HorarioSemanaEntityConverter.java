package com.business.manager.horario.converters.ubicacion;

import com.business.manager.horario.dao.entities.HorarioUbicacion;
import com.business.manager.horario.model.ubicacion.HorarioSemanaModel;
import com.business.manager.horario.model.ubicacion.HorarioUbicacionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class HorarioSemanaEntityConverter implements Converter<HorarioSemanaModel, Set<HorarioUbicacion>> {

    @Autowired
    private HorarioUbicacionEntityConverter horarioUbicacionEntityConverter;

    @Override
    public Set<HorarioUbicacion> convert(HorarioSemanaModel horarioSemanaModel) {
        Set<HorarioUbicacion> horariosByUbicacion = new HashSet<>();

        addNonNullHorarioUbicacion(horariosByUbicacion, horarioSemanaModel.getLunes());
        addNonNullHorarioUbicacion(horariosByUbicacion, horarioSemanaModel.getMartes());
        addNonNullHorarioUbicacion(horariosByUbicacion, horarioSemanaModel.getMiercoles());
        addNonNullHorarioUbicacion(horariosByUbicacion, horarioSemanaModel.getJueves());
        addNonNullHorarioUbicacion(horariosByUbicacion, horarioSemanaModel.getViernes());
        addNonNullHorarioUbicacion(horariosByUbicacion, horarioSemanaModel.getSabado());
        addNonNullHorarioUbicacion(horariosByUbicacion, horarioSemanaModel.getDomingo());

        return horariosByUbicacion;
    }

    private void addNonNullHorarioUbicacion(Set<HorarioUbicacion> horariosByUbicacion, HorarioUbicacionModel model) {
        if(Objects.nonNull(model)) {
            horariosByUbicacion.add(horarioUbicacionEntityConverter.convert(model));
        }
    }
}
