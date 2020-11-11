package com.business.manager.horario.services.implementations;

import com.business.manager.horario.dao.entities.Parametro;
import com.business.manager.horario.dao.repositories.ParametroRepository;
import com.business.manager.horario.enums.ComponentEnum;
import com.business.manager.horario.enums.StreamAction;
import com.business.manager.horario.exceptions.NoDataFoundException;
import com.business.manager.horario.exceptions.OperationNotPossibleException;
import com.business.manager.horario.exceptions.errors.ErrorEnum;
import com.business.manager.horario.model.ParametroModel;
import com.business.manager.horario.model.streams.ParametroStreamModel;
import com.business.manager.horario.services.ParametroService;
import com.business.manager.horario.streams.HorarioOutputStreams;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ParametroServiceImpl implements ParametroService {

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    private HorarioOutputStreams horarioOutputStreams;

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
        if(ComponentEnum.HORARIO == ComponentEnum.valueOf(parametroModel.getComponente())) {
            mapParametros.put(parametro.getNombre(), parametro.getValor());
        }else {
            parametroModel = conversionService.convert(parametro, ParametroModel.class);
            sendParametroMessage(ParametroStreamModel.of(StreamAction.UPSERT, parametroModel));
        }

        return parametroModel;
    }

    @Override
    public ParametroModel updateParametro(ParametroModel parametroModel) {
        Parametro parametro = parametroRepository.save(conversionService.convert(parametroModel, Parametro.class));
        mapParametros.put(parametro.getNombre(), parametro.getValor());

        parametroModel = conversionService.convert(parametro, ParametroModel.class);
        sendParametroMessage(ParametroStreamModel.of(StreamAction.UPSERT, parametroModel));
        return parametroModel;
    }

    @Override
    public void deleteParametro(Integer idParametro) {
        Parametro parametro = parametroRepository.findById(idParametro).get();
        sendParametroMessage(
                ParametroStreamModel.of(StreamAction.DELETE, conversionService.convert(parametro, ParametroModel.class)));
        mapParametros.remove(parametro.getNombre());
        parametroRepository.deleteById(idParametro);
    }


    @Override
    public List<ParametroModel> findAllParametro(){
        List<Parametro> parametros = parametroRepository.findAll();

        if(CollectionUtils.isEmpty(parametros)) {
            throw new NoDataFoundException(ErrorEnum.PARAMETROS_NOT_FOUND);
        }

        return parametros.stream()
                .map(p -> conversionService.convert(p, ParametroModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void sendAllParametros(){
        List<ParametroModel> parametroModels = findAllParametro();
        parametroModels.stream()
                .map(param -> ParametroStreamModel.of(StreamAction.UPSERT, param))
                .forEach(this::sendParametroMessage);
    }

    private void sendParametroMessage(ParametroStreamModel parametro){
        MessageChannel messageChannel = horarioOutputStreams.outboundParametros();
        messageChannel.send(MessageBuilder
                .withPayload(parametro)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
    }

    @Override
    public ComponentEnum[] getAllComponentes(){
        return ComponentEnum.values();
    }
}
