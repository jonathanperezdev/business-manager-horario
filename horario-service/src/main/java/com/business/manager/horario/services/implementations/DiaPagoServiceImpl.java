package com.business.manager.horario.services.implementations;

import com.business.manager.horario.dao.entities.DiaPago;
import com.business.manager.horario.dao.entities.Empleado;
import com.business.manager.horario.dao.entities.HorarioUbicacion;
import com.business.manager.horario.dao.repositories.DiaPagoRepository;
import com.business.manager.horario.dao.repositories.HorarioUbicacionRepository;
import com.business.manager.horario.enums.DiaEnum;
import com.business.manager.horario.exceptions.NoDataFoundException;
import com.business.manager.horario.exceptions.errors.ErrorEnum;
import com.business.manager.horario.model.empleado.DiaPagoModel;
import com.business.manager.horario.model.empleado.HorarioEmpleadoModel;
import com.business.manager.horario.services.DiaPagoService;
import com.business.manager.horario.services.RecargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiaPagoServiceImpl implements DiaPagoService {

    @Autowired
    private DiaPagoRepository diaPagoRepository;

    @Autowired
    private HorarioUbicacionRepository horarioUbicacionRepository;

    @Autowired
    private RecargoService recargoService;

    @Autowired
    @Qualifier("customConversionService")
    private ConversionService conversionService;

    @Override
    public DiaPago createDiaPago(Empleado empleado,
                                  LocalDate fecha,
                                  Long idSemana) {

        //Get default horario for a date
        HorarioUbicacion horarioUbicacion = horarioUbicacionRepository.findHorarioDefaultByUbicacionAndDia(
                empleado.getUbicacion().getId(),
                DiaEnum.diaEnumOf(fecha));

        //It is not a labor day
        if(Objects.isNull(horarioUbicacion)) {
            return null;
        }

        DiaPago diaPago = new DiaPago();
        diaPago.setIdSemana(idSemana);
        diaPago.setIdUbicacion(empleado.getUbicacion().getId());
        diaPago.setEmpleado(empleado);

        diaPago.setFechaInicio(LocalDateTime.of(fecha, horarioUbicacion.getHoraInicio()));
        boolean diaDespues = (horarioUbicacion.getHoraInicio()).isAfter(horarioUbicacion.getHoraFin());
        diaPago.setFechaFin(LocalDateTime.of(fecha.plusDays(diaDespues?1:0), horarioUbicacion.getHoraFin()));
        diaPago.setRecargos(recargoService.calcularRecargos(diaPago));

        diaPagoRepository.save(diaPago);

        return diaPago;
    }

    @Override
    public List<HorarioEmpleadoModel> getHorarioEmpleadoBySemana(Long idSemana){
        List<DiaPago> diasPago = diaPagoRepository.findBySemana(idSemana);
        return getHorarioEmpleadoBySemana(diasPago);
    }

    @Override
    public List<HorarioEmpleadoModel> getHorarioEmpleadoBySemanaAndUbicacion(Long idSemana, Integer idUbicacion){
        List<DiaPago> diasPago = diaPagoRepository.findBySemanaAndUbicacion(idSemana, idUbicacion);

        if(diasPago.isEmpty()) {
            throw new NoDataFoundException(ErrorEnum.PERIODO_PAGO_HORARIO_EMPLEADO_NOT_FOUND, idSemana, idUbicacion);
        }

        return getHorarioEmpleadoBySemana(diasPago);
    }

    private List<HorarioEmpleadoModel> getHorarioEmpleadoBySemana(List<DiaPago> diasPago) {
        Map<Long, List<DiaPago>> diasPagoByEmpleado = diasPago.stream().collect(Collectors.groupingBy(dia -> dia.getEmpleado().getId()));

        List<HorarioEmpleadoModel> horarioEmpleadoBySemana = new ArrayList<>();
        diasPagoByEmpleado.forEach(
                (idEmpleado, diasPagoEmpleado) ->
                        horarioEmpleadoBySemana.add(conversionService.convert(diasPagoEmpleado, HorarioEmpleadoModel.class)));

        return horarioEmpleadoBySemana;
    }

    @Override
    public HorarioEmpleadoModel getHorarioEmpleado(Long idSemana, Long idEmpleado) {
        List<DiaPago> diasPagoEmpleado = diaPagoRepository.findBySemanaAndEmpleado(idSemana, idEmpleado);
        return conversionService.convert(diasPagoEmpleado, HorarioEmpleadoModel.class);
    }

    @Override
    public HorarioEmpleadoModel updateHorarioEmpleado(HorarioEmpleadoModel horarioEmpleadoModel) {
        List<DiaPago> horarioSemanaByEmpleado = updateDiasPagoByEmpleado(horarioEmpleadoModel);

        return conversionService.convert(horarioSemanaByEmpleado, HorarioEmpleadoModel.class);
    }

    private List<DiaPago> updateDiasPagoByEmpleado(HorarioEmpleadoModel horarioEmpleadoModel) {
        List<DiaPago> horariosByEmpleado = new ArrayList<>();

        Optional.ofNullable(horarioEmpleadoModel.getLunes()).map(this::modifyDiaPagoEntityOf).map(horariosByEmpleado::add);
        Optional.ofNullable(horarioEmpleadoModel.getMartes()).map(this::modifyDiaPagoEntityOf).map(horariosByEmpleado::add);
        Optional.ofNullable(horarioEmpleadoModel.getMiercoles()).map(this::modifyDiaPagoEntityOf).map(horariosByEmpleado::add);
        Optional.ofNullable(horarioEmpleadoModel.getJueves()).map(this::modifyDiaPagoEntityOf).map(horariosByEmpleado::add);
        Optional.ofNullable(horarioEmpleadoModel.getViernes()).map(this::modifyDiaPagoEntityOf).map(horariosByEmpleado::add);
        Optional.ofNullable(horarioEmpleadoModel.getSabado()).map(this::modifyDiaPagoEntityOf).map(horariosByEmpleado::add);
        Optional.ofNullable(horarioEmpleadoModel.getDomingo()).map(this::modifyDiaPagoEntityOf).map(horariosByEmpleado::add);

        return horariosByEmpleado;
    }

    public DiaPago modifyDiaPagoEntityOf(DiaPagoModel diapagoModel) {
        DiaPago diaPago = diaPagoRepository.findById(diapagoModel.getId()).get();

        deleteRecargosOf(diaPago);

        diaPago.setFechaInicio((LocalDateTime)diapagoModel.getFechaInicio());
        diaPago.setFechaFin((LocalDateTime)diapagoModel.getFechaFin());
        diaPago.getRecargos().addAll(recargoService.calcularRecargos(diaPago));

        return diaPagoRepository.save(diaPago);
    }

    private void deleteRecargosOf(DiaPago diaPago) {
        diaPago.getRecargos().clear();
        diaPagoRepository.save(diaPago);
    }
}
