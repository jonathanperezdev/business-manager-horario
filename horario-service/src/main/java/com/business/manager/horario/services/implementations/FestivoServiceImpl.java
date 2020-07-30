package com.business.manager.horario.services.implementations;

import com.business.manager.horario.dao.entities.Festivo;
import com.business.manager.horario.dao.repositories.FestivoRepository;
import com.business.manager.horario.exceptions.NoDataFoundException;
import com.business.manager.horario.exceptions.OperationNotPossibleException;
import com.business.manager.horario.exceptions.errors.ErrorEnum;
import com.business.manager.horario.model.FestivoModel;
import com.business.manager.horario.services.FestivoService;
import com.business.manager.horario.services.ParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FestivoServiceImpl implements FestivoService {

    @Autowired
    @Qualifier("customConversionService")
    private ConversionService conversionService;

    @Autowired
    private FestivoRepository festivoRepository;

    @Autowired
    private ParametroService parametroService;

    private Set<LocalDate> festivos;

    @PostConstruct
    public void init() {
        festivos = festivoRepository
                .findByYearsOrderByFestivo(Arrays.asList(LocalDate.now().getYear() -1,
                        LocalDate.now().getYear()))
                .stream()
                .map(Festivo::getFestivo)
                .collect(Collectors.toSet());
    }

    @Override
    public void validarFestivosCreados() {
        int minimoFestivosAno = Integer.parseInt(parametroService.getValueOfParametro("MINIMO_FESTIVOS_POR_ANO"));

        if(CollectionUtils.isEmpty(this.festivos)) {
            throw new OperationNotPossibleException(ErrorEnum.FESTIVO_NO_CREADOS, LocalDate.now().getYear());
        }

        if(this.festivos.size() < minimoFestivosAno){
            throw new OperationNotPossibleException(ErrorEnum.NUMERO_FESTIVOS_BAJO, LocalDate.now().getYear(), minimoFestivosAno);
        }
    }

    @Override
    public FestivoModel createFestivo(FestivoModel festivoModel) {
        Festivo festivo = festivoRepository.findByFestivo(LocalDate.parse(festivoModel.getFestivo()));

        if(!Objects.isNull(festivo)){
            throw new OperationNotPossibleException(ErrorEnum.FESTIVO_ALREADY_EXIST, LocalDate.parse(festivoModel.getFestivo()));
        }

        festivo = festivoRepository.save(conversionService.convert(festivoModel, Festivo.class));

        return conversionService.convert(festivo, FestivoModel.class);
    }

    @Override
    public List<FestivoModel> findByYear(Integer year){
        List<Festivo> listFestivos = festivoRepository.findByYearsOrderByFestivo(Arrays.asList(year));

        if(CollectionUtils.isEmpty(listFestivos)) {
            throw new NoDataFoundException(ErrorEnum.FESTIVO_NOT_FOUND_BY_YEAR, year);
        }

        return toModel(listFestivos);
    }

    @Override
    public List<Integer> findYears(){
        return festivoRepository.findAllYearsOrderByYear();
    }

    @Override
    public boolean isFestivo(LocalDate fecha) {
        return festivos.contains(fecha);
    }

    @Override
    public void deleteFestivo(Integer idFestivo){
        festivoRepository.deleteById(idFestivo);
    }

    private List<FestivoModel> toModel(List<Festivo> listFestivos) {
        return listFestivos
                .stream()
                .map(festivo -> conversionService.convert(festivo, FestivoModel.class))
                .collect(Collectors.toList());
    }
}
