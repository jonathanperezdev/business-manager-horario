package com.business.manager.horario.services;

import com.business.manager.horario.model.empleado.EmpleadoModel;

public interface EmpleadoService {
    void upsertEmpleado(EmpleadoModel empleadoModel);
    void deleteEmpleado(Long idEmpleado);
}
