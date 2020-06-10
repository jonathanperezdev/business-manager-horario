package com.business.manager.horario.services.implementations;

import com.business.manager.horario.dao.entities.*;
import com.business.manager.horario.dao.repositories.EmpleadoRepository;
import com.business.manager.horario.dao.repositories.PeriodoPagoRepository;
import com.business.manager.horario.enums.DiaEnum;
import com.business.manager.horario.exceptions.OperationNotPossibleException;
import com.business.manager.horario.exceptions.errors.ErrorEnum;
import com.business.manager.horario.model.PeriodoPagoModel;
import com.business.manager.horario.services.DiaPagoService;
import com.business.manager.horario.services.ParametroService;
import com.business.manager.horario.services.PeriodoPagoService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class PeriodoPagoServiceImpl implements PeriodoPagoService {

    @Autowired
    private PeriodoPagoRepository periodoPagoRepository;

    @Autowired
    private ParametroService parametroService;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private DiaPagoService diaPagoService;

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

        Long diasPeriodoPago = DAYS.between(fechaInicial ,fechaFin);
        if(diasPeriodoPago > maximosDiasPeriodoPago) {
            throw new OperationNotPossibleException(ErrorEnum.PERIODO_PAGO_NO_PERMITIDO, maximosDiasPeriodoPago);
        }

        PeriodoPago periodoPago = conversionService.convert(periodoPagoModel, PeriodoPago.class);
        periodoPago.setSemanasPago(calcularSemanas(periodoPago));

        periodoPago = periodoPagoRepository.save(periodoPago);
        createDiasPagoAllEmployees(periodoPago);

        return conversionService.convert(periodoPago, PeriodoPagoModel.class);
    }

    private List<SemanaPago> calcularSemanas(PeriodoPago periodoPago) {
        List<SemanaPago> listSemanas = new ArrayList<>();

        LocalDate fechaInicio = periodoPago.getFechaInicio();
        LocalDate fechaFin = null;

        SemanaPago semanaEntity;
        Short numeroSemana = 1;

        do {

            fechaFin = fechaInicio.plusDays(DiaEnum.SUNDAY.getOrden() - DiaEnum.diaEnumOf(fechaInicio).getOrden());

            if(fechaFin.isAfter(periodoPago.getFechaFin())){
                fechaFin = periodoPago.getFechaFin();
            }

            semanaEntity = new SemanaPago();
            semanaEntity.setFechaInicio(fechaInicio);
            semanaEntity.setFechaFin(fechaFin);
            semanaEntity.setNumeroSemana(numeroSemana);
            semanaEntity.setPeriodo(periodoPago);

            listSemanas.add(semanaEntity);

            numeroSemana ++;
            fechaInicio = fechaFin.plusDays(1);
        }while(fechaFin.isBefore(periodoPago.getFechaFin()));

        return listSemanas;
    }

    private void createDiasPagoAllEmployees(PeriodoPago periodoPago){

        //Creating day of payment for all the employees
        List<Empleado> empleados = empleadoRepository.findAll();
        LocalDate fechaDia;

        for(int i = 0 ; i < DAYS.between(periodoPago.getFechaInicio(),periodoPago.getFechaFin()); i++) {
            fechaDia = periodoPago.getFechaInicio().plusDays(i);

            for (Empleado empleado: empleados) {
                diaPagoService.createDiaPago(empleado,
                        fechaDia,
                        getIdSemanaByFecha(periodoPago.getSemanasPago(), fechaDia));
            }
        }
    }

    private Long getIdSemanaByFecha(List<SemanaPago> semanasPago, LocalDate fecha) {
        return semanasPago.stream()
                .filter(s -> isBetweenInclusive(s.getFechaInicio(), s.getFechaFin(), fecha))
                .findFirst()
                .get()
                .getId();
    }

    boolean isBetweenInclusive(LocalDate inicio, LocalDate fin, LocalDate fecha) {
        return !fecha.isBefore(inicio) && !fecha.isAfter(fin);
    }
}
