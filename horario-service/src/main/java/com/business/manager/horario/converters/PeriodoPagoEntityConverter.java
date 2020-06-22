package com.business.manager.horario.converters;

import com.business.manager.horario.dao.entities.PeriodoPago;
import com.business.manager.horario.model.PeriodoPagoModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PeriodoPagoEntityConverter implements Converter<PeriodoPagoModel, PeriodoPago> {

    @Override
    public PeriodoPago convert(PeriodoPagoModel periodoPagoModel) {
        PeriodoPago entity = new PeriodoPago();

        entity.setId(periodoPagoModel.getId());
        entity.setFechaInicio((LocalDate)periodoPagoModel.getFechaInicio());
        entity.setFechaFin((LocalDate)periodoPagoModel.getFechaFin());

        return entity;
    }
}


