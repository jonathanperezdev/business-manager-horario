package com.business.manager.horario.converters.empleado;

import com.business.manager.horario.dao.entities.DiaPago;
import com.business.manager.horario.dao.entities.Empleado;
import com.business.manager.horario.dao.repositories.EmpleadoRepository;
import com.business.manager.horario.enums.DiaEnum;
import com.business.manager.horario.model.empleado.DiaPagoModel;
import com.business.manager.horario.model.empleado.EmpleadoModel;
import com.business.manager.horario.model.empleado.HorarioEmpleadoModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class HorarioEmpleadoModelConverter implements Converter<List<DiaPago>, HorarioEmpleadoModel> {

    @Autowired
    private DiaPagoModelConverter diaPagoModelConverter;

    @Autowired
    private ModelMapper modelMapper;

    private final Function<DiaPagoModel, DiaEnum> toDiaEnumFunction = dia -> DiaEnum.diaEnumOf(LocalDate.from(dia.getFechaInicio()));

    @Override
    public HorarioEmpleadoModel convert(List<DiaPago> diasPago) {
        Map<DiaEnum, DiaPagoModel> horarioUbicacionMap =
                diasPago
                        .stream()
                        .map(diaPagoModelConverter::convert)
                        .collect(Collectors.toMap( toDiaEnumFunction, Function.identity()));

        HorarioEmpleadoModel horarioEmpleadoModel = new HorarioEmpleadoModel();

        Empleado empleado = diasPago.stream().findFirst().get().getEmpleado();
        horarioEmpleadoModel.setEmpleado(modelMapper.map(empleado, EmpleadoModel.class));

        horarioEmpleadoModel.setLunes(horarioUbicacionMap.get(DiaEnum.MONDAY));
        horarioEmpleadoModel.setMartes(horarioUbicacionMap.get(DiaEnum.TUESDAY));
        horarioEmpleadoModel.setMiercoles(horarioUbicacionMap.get(DiaEnum.WEDNESDAY));
        horarioEmpleadoModel.setJueves(horarioUbicacionMap.get(DiaEnum.THURSDAY));
        horarioEmpleadoModel.setViernes(horarioUbicacionMap.get(DiaEnum.FRIDAY));
        horarioEmpleadoModel.setSabado(horarioUbicacionMap.get(DiaEnum.SATURDAY));
        horarioEmpleadoModel.setDomingo(horarioUbicacionMap.get(DiaEnum.SUNDAY));

        return horarioEmpleadoModel;
    }
}
