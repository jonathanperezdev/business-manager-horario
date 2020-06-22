package com.business.manager.horario.components;

import com.business.manager.horario.dao.entities.DiaPago;
import com.business.manager.horario.dao.entities.Recargo;
import com.business.manager.horario.enums.ConceptoRecargoEnum;
import com.business.manager.horario.services.ParametroService;
import com.business.manager.horario.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.Set;

@Component
@Qualifier("recargoNocturnoCalculator")
public class RecargoNocturnoCalculator extends AbstractRecargoCalculator{

    @Autowired
    private ParametroService parametroService;

    private LocalTime horarioNocturnoInicio;
    private LocalTime horarioNocturnoFin;

    @PostConstruct
    private void init() {
        horarioNocturnoInicio = DateUtil.getLocalTimeOf(parametroService.getValueOfParametro("HORARIO_NOCTURNO_INICIO"));
        horarioNocturnoFin = DateUtil.getLocalTimeOf(parametroService.getValueOfParametro("HORARIO_NOCTURNO_FIN"));
    }

    @Override
    public Set<Recargo> calcularRecargos(DiaPago diaPago, Set<Recargo> recargos) {
        boolean fechaInicioAfterHorarioNocturno = diaPago.getFechaInicio().isAfter(LocalDateTime.of(diaPago.getFechaInicio().toLocalDate(), horarioNocturnoInicio));
        boolean fechaFinBeforeHorarioNocturno = diaPago.getFechaFin().isBefore(LocalDateTime.of(diaPago.getFechaInicio().plusDays(1).toLocalDate(), horarioNocturnoFin));
        boolean fechaFinAfterHorarioNocturno = diaPago.getFechaFin().isAfter(LocalDateTime.of(diaPago.getFechaInicio().toLocalDate(), horarioNocturnoInicio));

        Double horasPartir;
        if(!fechaFinAfterHorarioNocturno) {
            return recargos;
        }

        if(fechaInicioAfterHorarioNocturno && fechaFinBeforeHorarioNocturno) {
            recargos.stream().forEach(r -> r.setConcepto(definirNuevoConcepto(r.getConcepto(), ConceptoRecargoEnum.NOCTURNO)));
        }else {
            horasPartir = calcularHoras(diaPago.getFechaInicio(),LocalDateTime.of(diaPago.getFechaInicio().toLocalDate(), horarioNocturnoInicio));
            if(horasPartir > 0) {
                recargos = splitRecargo(diaPago,
                        recargos,
                        ConceptoRecargoEnum.NOCTURNO,
                        horasPartir);
            }

            horasPartir = calcularHoras(diaPago.getFechaInicio(),LocalDateTime.of(diaPago.getFechaFin().toLocalDate(), horarioNocturnoFin));
            if(horasPartir > 0) {
                recargos = splitRecargo(diaPago,
                        recargos,
                        ConceptoRecargoEnum.DIURNO,
                        horasPartir);
            }
        }
        return recargos;
    }
}
