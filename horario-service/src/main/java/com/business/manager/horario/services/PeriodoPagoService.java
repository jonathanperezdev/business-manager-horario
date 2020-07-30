package com.business.manager.horario.services;

import com.business.manager.horario.model.empleado.HorarioEmpleadoModel;
import com.business.manager.horario.model.empleado.PeriodoPagoModel;

import java.util.List;

public interface PeriodoPagoService {
    PeriodoPagoModel createPeriodoPago(PeriodoPagoModel periodoPago);
    List<Integer> findYears();
    List<PeriodoPagoModel> findByYear(Integer year);
    void deletePeriodoPago(Long idPeriodoPago);
    PeriodoPagoModel getPeridoPago(Long idPeriodoPago);

}
