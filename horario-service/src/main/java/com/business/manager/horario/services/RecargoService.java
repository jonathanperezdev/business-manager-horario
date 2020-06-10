package com.business.manager.horario.services;

import com.business.manager.horario.dao.entities.DiaPago;
import com.business.manager.horario.dao.entities.Recargo;

import java.util.Set;

public interface RecargoService {
    Set<Recargo> calcularRecargos(DiaPago diaPago);
}
