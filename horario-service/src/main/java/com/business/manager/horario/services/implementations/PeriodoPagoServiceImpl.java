package com.business.manager.horario.services.implementations;

import com.business.manager.horario.dao.entities.*;
import com.business.manager.horario.dao.repositories.DiaPagoRepository;
import com.business.manager.horario.dao.repositories.EmpleadoRepository;
import com.business.manager.horario.dao.repositories.PeriodoPagoRepository;
import com.business.manager.horario.enums.DiaEnum;
import com.business.manager.horario.enums.EstadoPago;
import com.business.manager.horario.exceptions.NoDataFoundException;
import com.business.manager.horario.exceptions.OperationNotPossibleException;
import com.business.manager.horario.exceptions.errors.ErrorEnum;
import com.business.manager.horario.model.empleado.PeriodoPagoModel;
import com.business.manager.horario.services.DiaPagoService;
import com.business.manager.horario.services.ParametroService;
import com.business.manager.horario.services.PeriodoPagoService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private DiaPagoRepository diaPagoRepository;

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

        for(int i = 0 ; i <= DAYS.between(periodoPago.getFechaInicio(),periodoPago.getFechaFin()); i++) {
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

    @Override
    public List<Integer> findYears(){
        return periodoPagoRepository.findAllYearsOrderByYear();
    }

    @Override
    public List<PeriodoPagoModel> findByYear(Integer year){
        List<PeriodoPago> listPeriodoPago = periodoPagoRepository.findByYearOrderByFechaInicio(year);

        if(CollectionUtils.isEmpty(listPeriodoPago)) {
            throw new NoDataFoundException(ErrorEnum.PERIODO_PAGO_NOT_FOUND_BY_YEAR, year);
        }

        return listPeriodoPago.stream()
                .map(periodo -> conversionService.convert(periodo, PeriodoPagoModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletePeriodoPago(Long idPeriodoPago){
        PeriodoPago periodoPago = periodoPagoRepository.findById(idPeriodoPago).get();

        if(EstadoPago.LIQUIDADO == periodoPago.getEstadoPago()) {
            throw new OperationNotPossibleException(ErrorEnum.PERIODO_PAGO_LIQUIDADO);
        }

        List<DiaPago> diasPago;
        for (SemanaPago semana :periodoPago.getSemanasPago()) {
            diasPago = diaPagoRepository.findBySemana(semana.getId());
            diasPago.forEach(diaPagoRepository::delete);
        }

        periodoPagoRepository.delete(periodoPago);
    }

    @Override
    public PeriodoPagoModel getPeridoPago(Long idPeriodoPago) {
        PeriodoPago periodoPago = periodoPagoRepository.findById(idPeriodoPago)
                .orElseThrow(() -> new NoDataFoundException(ErrorEnum.PERIODO_PAGO_NOT_FOUND, idPeriodoPago));

        return conversionService.convert(periodoPago, PeriodoPagoModel.class);
    }

}
