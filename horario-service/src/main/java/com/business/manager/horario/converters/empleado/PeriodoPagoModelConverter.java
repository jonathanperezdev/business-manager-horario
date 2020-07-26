package com.business.manager.horario.converters.empleado;

import com.business.manager.horario.dao.entities.PeriodoPago;
import com.business.manager.horario.model.empleado.PeriodoPagoModel;
import com.business.manager.horario.model.empleado.SemanaPagoModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PeriodoPagoModelConverter implements Converter<PeriodoPago, PeriodoPagoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PeriodoPagoModel convert(PeriodoPago periodoPago) {
        PeriodoPagoModel periodoPagoModel = new PeriodoPagoModel();

        periodoPagoModel.setId(periodoPago.getId());
        periodoPagoModel.setFechaInicio(periodoPago.getFechaInicio());
        periodoPagoModel.setFechaFin(periodoPago.getFechaFin());
        periodoPagoModel.setDiasLiquidados(1L+(ChronoUnit.DAYS.between(periodoPago.getFechaInicio(), periodoPago.getFechaFin())));

        Set<SemanaPagoModel> semanas = periodoPago.getSemanasPago().stream()
                .map(semana -> modelMapper.map(semana, SemanaPagoModel.class))
                .collect(Collectors.toSet());

        periodoPagoModel.setSemanas(semanas);

        return periodoPagoModel;
    }
}


