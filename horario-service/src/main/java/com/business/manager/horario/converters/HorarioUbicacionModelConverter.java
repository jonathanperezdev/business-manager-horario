package com.business.manager.horario.converters;

import com.business.manager.horario.dao.entities.HorarioUbicacion;
import com.business.manager.horario.enums.DiaEnum;
import com.business.manager.horario.model.HorarioUbicacionModel;
import com.business.manager.horario.util.DateUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class HorarioUbicacionModelConverter implements Converter<HorarioUbicacion, HorarioUbicacionModel> {

    @Override
    public HorarioUbicacionModel convert(HorarioUbicacion horarioUbicacion) {
        if(Objects.isNull(horarioUbicacion)) {
            return null;
        }

        HorarioUbicacionModel horarioUbicacionModel = new HorarioUbicacionModel();

        horarioUbicacionModel.setDia(horarioUbicacion.getDia().getDia());
        horarioUbicacionModel.setFechas(DateUtil.getStringOf(horarioUbicacion.getHoraInicio())+" - "+DateUtil.getStringOf(horarioUbicacion.getHoraFin()));
        //horarioUbicacionModel.setFechaInicioTemporal(horarioUbicacion.getHoraInicio());
        //horarioUbicacionModel.setFechaFinTemporal(horarioUbicacion.getHoraFin());
        horarioUbicacionModel.setId(horarioUbicacion.getId());

        return horarioUbicacionModel;
    }
}
