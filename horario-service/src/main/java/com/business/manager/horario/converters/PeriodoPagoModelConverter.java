package com.business.manager.horario.converters;

import com.business.manager.horario.dao.entities.PeriodoPago;
import com.business.manager.horario.model.PeriodoPagoModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component
public class PeriodoPagoModelConverter implements Converter<PeriodoPago, PeriodoPagoModel> {

    @Override
    public PeriodoPagoModel convert(PeriodoPago periodoPago) {
        PeriodoPagoModel periodoPagoModel = new PeriodoPagoModel();

        periodoPagoModel.setId(periodoPago.getId());
        periodoPagoModel.setFechaInicio(periodoPago.getFechaInicio());
        periodoPagoModel.setFechaFin(periodoPago.getFechaFin());
        periodoPagoModel.setDiasLiquidados(1L+(ChronoUnit.DAYS.between(periodoPago.getFechaInicio(), periodoPago.getFechaFin())));

        return periodoPagoModel;
    }
}


