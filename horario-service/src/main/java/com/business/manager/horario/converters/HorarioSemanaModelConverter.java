package com.business.manager.horario.converters;

import com.business.manager.horario.dao.entities.HorarioUbicacion;
import com.business.manager.horario.enums.DiaEnum;
import com.business.manager.horario.model.HorarioSemanaModel;
import com.business.manager.horario.model.HorarioUbicacionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class HorarioSemanaModelConverter implements Converter<Set<HorarioUbicacion>, HorarioSemanaModel> {

    @Autowired
    private HorarioUbicacionModelConverter horarioUbicacionModelConverter;

    private final Function<HorarioUbicacionModel, DiaEnum> toDiaEnumFunction = horarioModel -> DiaEnum.diaEnumOf(horarioModel.getDia());

    @Override
    public HorarioSemanaModel convert(Set<HorarioUbicacion> listHorarioUbicacion) {
        Map<DiaEnum, HorarioUbicacionModel> horarioUbicacionMap =
                listHorarioUbicacion
                        .stream()
                        .map(horarioUbicacionModelConverter::convert)
                        .collect(Collectors.toMap( toDiaEnumFunction, Function.identity()));

        HorarioSemanaModel semanaModel = new HorarioSemanaModel();

        semanaModel.setLunes(horarioUbicacionMap.get(DiaEnum.MONDAY));
        semanaModel.setMartes(horarioUbicacionMap.get(DiaEnum.TUESDAY));
        semanaModel.setMiercoles(horarioUbicacionMap.get(DiaEnum.WEDNESDAY));
        semanaModel.setJueves(horarioUbicacionMap.get(DiaEnum.THURSDAY));
        semanaModel.setViernes(horarioUbicacionMap.get(DiaEnum.FRIDAY));
        semanaModel.setSabado(horarioUbicacionMap.get(DiaEnum.SATURDAY));
        semanaModel.setDomingo(horarioUbicacionMap.get(DiaEnum.SUNDAY));

        return semanaModel;
    }
}
