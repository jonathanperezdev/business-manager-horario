package com.business.manager.horario.converters.empleado;

import com.business.manager.horario.dao.entities.Empleado;
import com.business.manager.horario.model.empleado.EmpleadoModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoModelConverter implements Converter<Empleado, EmpleadoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EmpleadoModel convert(Empleado empleado) {
        return modelMapper.map(empleado, EmpleadoModel.class);
    }
}


