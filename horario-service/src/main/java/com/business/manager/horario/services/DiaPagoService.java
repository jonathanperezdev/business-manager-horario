package com.business.manager.horario.services;

import com.business.manager.horario.dao.entities.DiaPago;
import com.business.manager.horario.dao.entities.Empleado;
import com.business.manager.horario.model.empleado.HorarioEmpleadoModel;

import java.time.LocalDate;
import java.util.List;

public interface DiaPagoService {

    DiaPago createDiaPago(Empleado empleado,
                          LocalDate fecha,
                          Long idSemana);

    List<HorarioEmpleadoModel> getHorarioEmpleadoBySemana(Long idSemana);
    List<HorarioEmpleadoModel> getHorarioEmpleadoBySemanaAndUbicacion(Long idSemana, Integer idUbicacion);
    HorarioEmpleadoModel getHorarioEmpleado(Long idSemana, Long idEmpleado);
    HorarioEmpleadoModel updateHorarioEmpleado(HorarioEmpleadoModel horarioEmpleadoModel);
}
