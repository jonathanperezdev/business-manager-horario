package com.business.manager.horario.services.implementations;

import com.business.manager.horario.components.RecargoCalculator;
import com.business.manager.horario.dao.entities.DiaPago;
import com.business.manager.horario.dao.entities.Recargo;
import com.business.manager.horario.services.RecargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class RecargoServiceImpl implements RecargoService {

    @Autowired
    @Qualifier("recargoDiurnoCalculator")
    private RecargoCalculator recargoDiurnoCalculator;

    @Autowired
    @Qualifier("recargoFestivoCalculator")
    private RecargoCalculator recargoFestivoCalculator;

    @Autowired
    @Qualifier("recargoNocturnoCalculator")
    private RecargoCalculator recargoNocturnoCalculator;

    @Autowired
    @Qualifier("recargoExtraCalculator")
    private RecargoCalculator recargoExtraCalculator;

    private List<RecargoCalculator> recargoCalculators = new ArrayList<>();

    @PostConstruct
    private void init() {
        recargoCalculators.add(recargoDiurnoCalculator);
        recargoCalculators.add(recargoFestivoCalculator);
        recargoCalculators.add(recargoNocturnoCalculator);
        recargoCalculators.add(recargoExtraCalculator);
    }

    @Override
    public Set<Recargo> calcularRecargos(DiaPago diaPago) {
        Set<Recargo> recargos = new TreeSet<>(Comparator.comparing(Recargo::getLineaTiempo));

        for (RecargoCalculator calculator: recargoCalculators) {
            recargos = calculator.calcularRecargos(diaPago, recargos);
        }

        return recargos;
    }
}
