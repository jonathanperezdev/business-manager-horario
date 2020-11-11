package com.business.manager.horario.components.implementations;

import com.business.manager.horario.components.RecargoCalculator;
import com.business.manager.horario.dao.entities.DiaPago;
import com.business.manager.horario.dao.entities.Recargo;
import com.business.manager.horario.enums.ConceptoRecargoEnum;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public abstract class AbstractRecargoCalculator implements RecargoCalculator {
    public enum PosicionRecargo {
        INICIAL,
        FINAL
    };

    private static final int MINUTOS_POR_HORA = 60;
    private static final double HORAS_POR_DIA = 24;

    public abstract Set<Recargo> calcularRecargos(DiaPago diaPago, Set<Recargo> recargos);

    protected Set<Recargo> splitRecargo(DiaPago diaPago,
                                        Set<Recargo> recargos,
                                        ConceptoRecargoEnum concepto,
                                        Double horasPartir) {

        Set<Recargo> recargosResultado = new TreeSet<>(Comparator.comparing(Recargo::getLineaTiempo));

        Recargo recargoFinal;
        boolean isReacrgosSpilted = false;
        for(Recargo recargoInicial : recargos) {
            if(!isReacrgosSpilted && horasPartir.compareTo(recargoInicial.getLineaTiempo()) < 0) {
                recargoFinal = createRecargo(diaPago,
                        definirNuevoConcepto(recargoInicial.getConcepto(), concepto),
                        recargoInicial.getLineaTiempo() - horasPartir,
                        recargoInicial.getLineaTiempo());

                recargoInicial.setHoras(recargoInicial.getHoras() - (recargoInicial.getLineaTiempo() - horasPartir));
                recargoInicial.setLineaTiempo(horasPartir);

                recargosResultado.add(recargoFinal);

                isReacrgosSpilted = true;
            }else if(isReacrgosSpilted) {
                recargoInicial.setConcepto(definirNuevoConcepto(recargoInicial.getConcepto(), concepto));
            }
            recargosResultado.add(recargoInicial);
        }
        return recargosResultado;
    }

    protected ConceptoRecargoEnum definirNuevoConcepto(ConceptoRecargoEnum existente,
                                                     ConceptoRecargoEnum nuevo) {
        if(Objects.isNull(nuevo)) {
            return existente;
        }

        if(ConceptoRecargoEnum.FESTIVO == nuevo) {
            if(ConceptoRecargoEnum.DIURNO == existente) {
                return ConceptoRecargoEnum.FESTIVO_DIURNO;
            }else {
                return ConceptoRecargoEnum.FESTIVO_NOCTURNO;
            }
        }

        if(ConceptoRecargoEnum.NOCTURNO == nuevo) {
            if(ConceptoRecargoEnum.FESTIVO_DIURNO == existente) {
                return ConceptoRecargoEnum.FESTIVO_NOCTURNO;
            }else if(ConceptoRecargoEnum.DIURNO == existente) {
                return ConceptoRecargoEnum.NOCTURNO;
            }
        }

        if(ConceptoRecargoEnum.EXTRA == nuevo) {
            switch(existente) {
                case DIURNO:
                    return ConceptoRecargoEnum.EXTRA_DIURNO;
                case NOCTURNO:
                    return ConceptoRecargoEnum.EXTRA_NOCTURNO;
                case FESTIVO_DIURNO:
                    return ConceptoRecargoEnum.FESTIVO_EXTRA_DIURNO;
                default:
                    return ConceptoRecargoEnum.FESTIVO_EXTRA_NOCTURNO;
            }
        }

        return nuevo;
    }

    protected Recargo createRecargo(DiaPago diaPago, ConceptoRecargoEnum concepto, Double horas, Double lineaTiempo) {
        Recargo recargo = new Recargo();
        recargo.setDiaPago(diaPago);
        recargo.setHoras(horas);
        recargo.setConcepto(concepto);
        recargo.setLineaTiempo(lineaTiempo);

        return recargo;
    }

    protected Double calcularHoras(LocalDateTime inicio, LocalDateTime fin) {
        double minutos = inicio.until(fin, ChronoUnit.MINUTES);
        return minutos / MINUTOS_POR_HORA;
    }
}
