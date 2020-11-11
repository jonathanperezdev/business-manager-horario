package com.business.manager.horario.converters.empleado;

import com.business.manager.horario.dao.entities.Empleado;
import com.business.manager.horario.dao.repositories.UbicacionRepository;
import com.business.manager.horario.model.empleado.EmpleadoModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmpleadoEntityConverter implements Converter<EmpleadoModel, Empleado> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Override
    public Empleado convert(EmpleadoModel empleadoModel) {
        Empleado empleado = modelMapper.map(empleadoModel, Empleado.class);
        Optional.ofNullable(empleadoModel.getIdUbicacion())
                .ifPresent(idUbicacion -> empleado.setUbicacion(ubicacionRepository.findById(idUbicacion).get()));
        return empleado;
    }
}


