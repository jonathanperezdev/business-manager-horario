package com.business.manager.horario.services.implementations;

import com.business.manager.horario.dao.entities.Parametro;
import com.business.manager.horario.dao.entities.PeriodoPago;
import com.business.manager.horario.dao.repositories.PeriodoPagoRepository;
import com.business.manager.horario.exceptions.OperationNotPossibleException;
import com.business.manager.horario.exceptions.errors.ErrorEnum;
import com.business.manager.horario.model.PeriodoPagoModel;
import com.business.manager.horario.services.ParametroService;
import com.business.manager.horario.services.PeriodoPagoService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class PeriodoPagoServiceImpl implements PeriodoPagoService {

    @Autowired
    private PeriodoPagoRepository periodoPagoRepository;

    @Autowired
    private ParametroService parametroService;

    @Autowired
    @Qualifier("customConversionService")
    private ConversionService conversionService;

    public PeriodoPagoModel createPeriodoPago(PeriodoPagoModel periodoPagoModel) {
        Long maximosDiasPeriodoPago = NumberUtils.createLong(parametroService.getValueOfParametro("MAXIMO_DIAS_PERIODO_PAGO"));

        LocalDate fechaInicial = (LocalDate)periodoPagoModel.getFechaInicio();
        LocalDate fechaFin = (LocalDate)periodoPagoModel.getFechaFin();

        if(fechaInicial.isAfter(fechaFin)) {
            throw new OperationNotPossibleException(ErrorEnum.PERIODO_PAGO_FECHA_INICIO_SUPERIOR,
                    periodoPagoModel.getFechaInicio(),
                    periodoPagoModel.getFechaFin());
        }

        if(Objects.nonNull(periodoPagoRepository.findByFechaInicioGreaterThanEqualAndFechaFinLessThanEqual(fechaInicial, fechaFin))) {
            throw new OperationNotPossibleException(ErrorEnum.PERIODO_PAGO_EXISTENTE,
                    periodoPagoModel.getFechaInicio(),
                    periodoPagoModel.getFechaFin());
        }

        Long diasPeriodoPago = ChronoUnit.DAYS.between(fechaInicial ,fechaFin);
        if(diasPeriodoPago > maximosDiasPeriodoPago) {
            throw new OperationNotPossibleException(ErrorEnum.PERIODO_PAGO_NO_PERMITIDO, maximosDiasPeriodoPago);
        }

        PeriodoPago periodoPago = periodoPagoRepository.save(conversionService.convert(periodoPagoModel, PeriodoPago.class));

        return conversionService.convert(periodoPago, PeriodoPagoModel.class);
    }
}
