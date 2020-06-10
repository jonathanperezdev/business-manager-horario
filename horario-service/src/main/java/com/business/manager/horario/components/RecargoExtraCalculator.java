package com.business.manager.horario.components;

import com.business.manager.horario.dao.entities.DiaPago;
import com.business.manager.horario.dao.entities.Recargo;
import com.business.manager.horario.dao.repositories.RecargoRepository;
import com.business.manager.horario.enums.ConceptoRecargoEnum;
import com.business.manager.horario.services.ParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Set;

@Component
@Qualifier("recargoExtraCalculator")
public class RecargoExtraCalculator extends AbstractRecargoCalculator {

    @Autowired
    private RecargoRepository recargoRepository;

    @Autowired
    private ParametroService parametroService;

    private Double horasLaborablesSemanales;
    private Double horasLaborablesDia;

    @PostConstruct
    private void init() {
        horasLaborablesSemanales = Double.valueOf(parametroService.getValueOfParametro("HORAS_LABORALES_SEMANA"));
        horasLaborablesDia = Double.valueOf(parametroService.getValueOfParametro("HORAS_LABORALES_DIA"));
    }

    @Override
    public Set<Recargo> calcularRecargos(DiaPago diaPago, Set<Recargo> recargos) {
        Double horasTotalesRecargos = recargos.stream().mapToDouble(Recargo::getHoras).sum();
        Double horasAcumuladas = ObjectUtils.defaultIfNull(
                recargoRepository.findHorasAcumuladasSemanaPorEmpleado(diaPago.getFechaInicio(), diaPago.getIdEmpleado()),
                0D);
        horasAcumuladas = horasAcumuladas + horasTotalesRecargos;

        Double horasPartir;

        //True - The worked hours are bigger than the hours in a week 48
        if(horasAcumuladas.compareTo(horasLaborablesSemanales) > 0) {
            horasPartir = (horasLaborablesSemanales - horasAcumuladas);

            if(horasPartir > 0D) {
                //One part of the hours are extra
                recargos = splitRecargo(diaPago, recargos, ConceptoRecargoEnum.EXTRA, horasPartir);
            }else {
                //All hours are extra
                recargos.stream().forEach(r ->  r.setConcepto(definirNuevoConcepto(r.getConcepto(), ConceptoRecargoEnum.EXTRA)));
            }
        //True - The worked hours are bigger than the hours in a day - 8
        }else if(horasTotalesRecargos.compareTo(horasLaborablesDia) > 0) {
            recargos = splitRecargo(diaPago, recargos, ConceptoRecargoEnum.EXTRA, horasLaborablesDia);
        }
        return recargos;
    }
}
