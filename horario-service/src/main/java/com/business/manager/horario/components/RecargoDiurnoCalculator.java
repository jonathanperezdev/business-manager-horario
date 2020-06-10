package com.business.manager.horario.components;

import com.business.manager.horario.dao.entities.DiaPago;
import com.business.manager.horario.dao.entities.Recargo;
import com.business.manager.horario.enums.ConceptoRecargoEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Qualifier("recargoDiurnoCalculator")
public class RecargoDiurnoCalculator extends AbstractRecargoCalculator{

    @Override
    public Set<Recargo> calcularRecargos(DiaPago diaPago, Set<Recargo> recargos) {
        double horasRecargo = calcularHoras(diaPago.getFechaInicio(), diaPago.getFechaFin());
        recargos.add(createRecargo(diaPago,
                ConceptoRecargoEnum.DIURNO,
                horasRecargo,
                horasRecargo));

        return recargos;
    }
}
