package com.business.manager.horario.converters.empleado;

import com.business.manager.horario.dao.entities.DiaPago;
import com.business.manager.horario.dao.entities.Recargo;
import com.business.manager.horario.dao.repositories.EmpleadoRepository;
import com.business.manager.horario.model.empleado.DiaPagoModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DiaPagoModelConverter implements Converter<DiaPago, DiaPagoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public DiaPagoModel convert(DiaPago diaPago) {
        DiaPagoModel diaPagoModel = modelMapper.map(diaPago, DiaPagoModel.class);
        diaPagoModel.setHoras(diaPago.getRecargos().stream().mapToDouble(Recargo::getHoras).sum());
        diaPagoModel.setDetalleRecargos(diaPago.getRecargos().stream().map(Recargo::toString).collect(Collectors.toList()));

        return diaPagoModel;
    }
}
