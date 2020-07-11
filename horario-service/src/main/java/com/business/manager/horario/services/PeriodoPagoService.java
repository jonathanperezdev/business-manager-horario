package com.business.manager.horario.services;

import com.business.manager.horario.model.PeriodoPagoModel;
import com.business.manager.horario.model.SemanaPagoModel;

import java.util.List;

public interface PeriodoPagoService {
    PeriodoPagoModel createPeriodoPago(PeriodoPagoModel periodoPago);
    List<Integer> findYears();
    List<PeriodoPagoModel> findByYear(Integer year);
    void deletePeriodoPago(Long idPeriodoPago);
    List<SemanaPagoModel> getSemanasPago(Long idPeriodoPago);
}
