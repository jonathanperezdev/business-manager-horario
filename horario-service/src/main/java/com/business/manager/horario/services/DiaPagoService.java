package com.business.manager.horario.services;

import com.business.manager.horario.dao.entities.DiaPago;
import com.business.manager.horario.dao.entities.Empleado;

import java.time.LocalDate;

public interface DiaPagoService {

    DiaPago createDiaPago(Empleado empleado,
                          LocalDate fecha,
                          Long idSemana);
}
