package com.business.manager.horario.services.implementations;

import com.business.manager.horario.dao.entities.DiaPago;
import com.business.manager.horario.dao.entities.Empleado;
import com.business.manager.horario.dao.entities.HorarioUbicacion;
import com.business.manager.horario.dao.repositories.DiaPagoRepository;
import com.business.manager.horario.dao.repositories.HorarioUbicacionRepository;
import com.business.manager.horario.enums.DiaEnum;
import com.business.manager.horario.services.DiaPagoService;
import com.business.manager.horario.services.RecargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class DiaPagoServiceImpl implements DiaPagoService {

    @Autowired
    private DiaPagoRepository diaPagoRepository;

    @Autowired
    private HorarioUbicacionRepository horarioUbicacionRepository;

    @Autowired
    private RecargoService recargoService;

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
        diaPago.setIdEmpleado(empleado.getId());

        diaPago.setFechaInicio(LocalDateTime.of(fecha, horarioUbicacion.getHoraInicio()));
        boolean diaDespues = (horarioUbicacion.getHoraInicio()).isAfter(horarioUbicacion.getHoraFin());
        diaPago.setFechaFin(LocalDateTime.of(fecha.plusDays(diaDespues?1:0), horarioUbicacion.getHoraFin()));
        diaPago.setRecargos(recargoService.calcularRecargos(diaPago));

        diaPagoRepository.save(diaPago);

        return diaPago;
    }
}
