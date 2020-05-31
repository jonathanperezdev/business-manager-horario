package com.business.manager.horario.services.implementations;

import com.business.manager.horario.dao.entities.HorarioUbicacion;
import com.business.manager.horario.dao.entities.Ubicacion;
import com.business.manager.horario.dao.repositories.UbicacionRepository;
import com.business.manager.horario.enums.DiaEnum;
import com.business.manager.horario.exceptions.NoDataFoundException;
import com.business.manager.horario.exceptions.errors.ErrorEnum;
import com.business.manager.horario.model.UbicacionModel;
import com.business.manager.horario.services.ParametroService;
import com.business.manager.horario.services.UbicacionService;
import com.business.manager.horario.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UbicacionServiceImpl implements UbicacionService {

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Autowired
    private ParametroService parametroService;

    @Autowired
    @Qualifier("customConversionService")
    private ConversionService conversionService;

    @Override
    public UbicacionModel findByIdUbicacion(Integer idUbicacion) {
        Optional<Ubicacion> optUbicacion = ubicacionRepository.findById(idUbicacion);

        if(!optUbicacion.isPresent()) {
            throw new NoDataFoundException(ErrorEnum.HORARIO_UBICACION_NOT_FOUND, "buscar");
        }

        UbicacionModel ubicacion = conversionService.convert(optUbicacion.get(), UbicacionModel.class);

        return ubicacion;
    }

    @Override
    public UbicacionModel getUbicacionHorarioDefault(Integer idUbicacion) {

        Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion).get();

        ubicacion.setHorariosByUbicacion(
                Arrays.stream(DiaEnum.values())
                        .map(this::createHorarioUbicacionDefault)
                        .collect(Collectors.toSet())
        );

        return conversionService.convert(ubicacion, UbicacionModel.class);
    }

    @Override
    public UbicacionModel updateUbicacion(UbicacionModel ubicacionModel) {
        Set<HorarioUbicacion> horariosByUbicacion = ubicacionRepository.findById(ubicacionModel.getId()).get().getHorariosByUbicacion();

        Ubicacion ubicacion = conversionService.convert(ubicacionModel, Ubicacion.class);
        for (HorarioUbicacion horario:ubicacion.getHorariosByUbicacion()) {
            horariosByUbicacion.remove(horario);
            horariosByUbicacion.add(horario);
        }

        ubicacion.setHorariosByUbicacion(horariosByUbicacion);
        ubicacion = ubicacionRepository.save(ubicacion);

        return conversionService.convert(ubicacion, UbicacionModel.class);
    }

    @Override
    public UbicacionModel deleteAllHorarioByUbicacion(Integer idUbicacion) {
        Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion).get();
        ubicacion.getHorariosByUbicacion().clear();

        ubicacionRepository.save(ubicacion);

        return conversionService.convert(ubicacion, UbicacionModel.class);
    }

    private HorarioUbicacion createHorarioUbicacionDefault(DiaEnum dia) {
        HorarioUbicacion horario = new HorarioUbicacion();

        LocalTime horaInicio = null;
        LocalTime horaFin = null;

        horario.setDia(dia);

        switch (dia) {
            case SATURDAY:
                horaInicio = DateUtil.getLocalTimeOf(parametroService.getValueOfParametro("HORA_INICIAL_SABADO"));
                horaFin = DateUtil.getLocalTimeOf(parametroService.getValueOfParametro("HORA_FINAL_SABADO"));
                break;
            case SUNDAY:
                horaInicio = DateUtil.getLocalTimeOf(parametroService.getValueOfParametro("HORA_INICIAL_DOMINGO"));
                horaFin = DateUtil.getLocalTimeOf(parametroService.getValueOfParametro("HORA_FINAL_DOMINGO"));
                break;
            default:
                horaInicio = DateUtil.getLocalTimeOf(parametroService.getValueOfParametro("HORA_INICIAL_ENTRE_SEMANA"));
                horaFin = DateUtil.getLocalTimeOf(parametroService.getValueOfParametro("HORA_FINAL_ENTRE_SEMANA"));
                break;
        }

        horario.setHoraInicio(horaInicio);
        horario.setHoraFin(horaFin);
        horario.setOrden(dia.getOrden());

        return horario;
    }

}
