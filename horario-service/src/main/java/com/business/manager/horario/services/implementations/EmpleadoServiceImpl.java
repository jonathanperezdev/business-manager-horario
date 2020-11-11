package com.business.manager.horario.services.implementations;

import com.business.manager.horario.dao.entities.Empleado;
import com.business.manager.horario.dao.repositories.EmpleadoRepository;
import com.business.manager.horario.model.empleado.EmpleadoModel;
import com.business.manager.horario.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    @Qualifier("customConversionService")
    private ConversionService conversionService;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public void upsertEmpleado(EmpleadoModel empleadoModel){
        Empleado empleado = conversionService.convert(empleadoModel, Empleado.class);
        empleadoRepository.save(empleado);
    }

    @Override
    public void deleteEmpleado(Long idEmpleado){
        empleadoRepository.findById(idEmpleado).ifPresent(empleadoRepository::delete);
    }
}
