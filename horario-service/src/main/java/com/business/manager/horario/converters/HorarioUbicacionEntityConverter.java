package com.business.manager.horario.converters;

import com.business.manager.horario.dao.entities.HorarioUbicacion;
import com.business.manager.horario.enums.DiaEnum;
import com.business.manager.horario.model.HorarioUbicacionModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Objects;

@Component
public class HorarioUbicacionEntityConverter implements Converter<HorarioUbicacionModel, HorarioUbicacion> {

    @Override
    public HorarioUbicacion convert(HorarioUbicacionModel horarioUbicacionModel) {
        if(Objects.isNull(horarioUbicacionModel)) {
            return null;
        }

        HorarioUbicacion horarioUbicacion = new HorarioUbicacion();

        DiaEnum dia = DiaEnum.diaEnumOf(horarioUbicacionModel.getDia());

        horarioUbicacion.setId(horarioUbicacionModel.getId());
        horarioUbicacion.setDia(dia);
        horarioUbicacion.setHoraInicio((LocalTime)horarioUbicacionModel.getFechaInicio());
        horarioUbicacion.setHoraFin((LocalTime)horarioUbicacionModel.getFechaFin());
        horarioUbicacion.setOrden(dia.getOrden());

        return horarioUbicacion;
    }
}
