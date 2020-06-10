package com.business.manager.horario.services.implementations;

import com.business.manager.horario.dao.entities.Parametro;
import com.business.manager.horario.dao.repositories.ParametroRepository;
import com.business.manager.horario.exceptions.NoDataFoundException;
import com.business.manager.horario.exceptions.OperationNotPossibleException;
import com.business.manager.horario.exceptions.errors.ErrorEnum;
import com.business.manager.horario.model.ParametroModel;
import com.business.manager.horario.services.ParametroService;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ParametroServiceImpl implements ParametroService {

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    @Qualifier("customConversionService")
    private ConversionService conversionService;

    private Map<String, String> mapParametros;

    @PostConstruct
    public void init() {
        mapParametros = parametroRepository
                .findAll()
                .stream()
                .collect(Collectors.toMap(Parametro::getNombre, Parametro::getValor));
    }

    @Override
    public String getValueOfParametro(String parametro) {
        String valor = mapParametros.get(parametro);

        if(StringUtils.isBlank(valor)) {
            throw new NoDataFoundException(ErrorEnum.PARAMETRO_NOT_FOUND, parametro);
        }

        return valor;
    }

    @Override
    public ParametroModel createParametro(ParametroModel parametroModel) {
        Parametro parametro = parametroRepository.findByNombre(parametroModel.getNombre());

        if(!Objects.isNull(parametro)) {
            throw new OperationNotPossibleException(ErrorEnum.PARAMETRO_ALREADY_EXIST, parametro.getNombre());
        }

        parametro = parametroRepository.save(conversionService.convert(parametroModel, Parametro.class));

        return conversionService.convert(parametro, ParametroModel.class);
    }

}
