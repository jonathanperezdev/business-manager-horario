package com.business.manager.horario.components;

import com.business.manager.horario.dao.entities.DiaPago;
import com.business.manager.horario.dao.entities.Recargo;

import java.util.Set;

public interface RecargoCalculator {
    Set<Recargo> calcularRecargos(DiaPago diaPago, Set<Recargo> recargos);
}
