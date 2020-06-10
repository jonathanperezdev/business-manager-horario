package com.business.manager.horario.components;

import com.business.manager.horario.dao.entities.DiaPago;
import com.business.manager.horario.dao.entities.Recargo;
import com.business.manager.horario.enums.ConceptoRecargoEnum;
import com.business.manager.horario.services.FestivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Qualifier("recargoFestivoCalculator")
public class RecargoFestivoCalculator extends AbstractRecargoCalculator{

    @Autowired
    private FestivoService festivoService;

    private static final double HORAS_POR_DIA = 24;

    @Override
    public Set<Recargo> calcularRecargos(DiaPago diaPago, Set<Recargo> recargos) {
        if (!festivoService.isFestivo(diaPago.getFechaInicio().toLocalDate())
                && !festivoService.isFestivo(diaPago.getFechaFin().toLocalDate())) {
            return recargos;
        }

        recargos.stream().forEach(r -> r.setConcepto(definirNuevoConcepto(r.getConcepto(), ConceptoRecargoEnum.FESTIVO)));

        if (festivoService.isFestivo(diaPago.getFechaInicio().toLocalDate())) {
            recargos = splitRecargo(diaPago, recargos, ConceptoRecargoEnum.DIURNO, HORAS_POR_DIA - diaPago.getFechaInicio().getHour());
        } else if(festivoService.isFestivo(diaPago.getFechaFin().toLocalDate())) {
            recargos = splitRecargo(diaPago, recargos, ConceptoRecargoEnum.FESTIVO, HORAS_POR_DIA - diaPago.getFechaFin().getHour());
        }

        return recargos;
    }
}
