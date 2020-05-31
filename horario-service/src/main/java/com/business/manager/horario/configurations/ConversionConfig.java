package com.business.manager.horario.configurations;

import com.business.manager.horario.converters.*;
import com.business.manager.horario.dao.entities.Festivo;
import com.business.manager.horario.dao.entities.Parametro;
import com.business.manager.horario.dao.entities.PeriodoPago;
import com.business.manager.horario.model.FestivoModel;
import com.business.manager.horario.model.ParametroModel;
import com.business.manager.horario.model.PeriodoPagoModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConversionConfig {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UbicacionModelConverter ubicacionModelConverter;

    @Autowired
    private UbicacionEntityConverter ubicacionEntityConverter;

    @Autowired
    private PeriodoPagoModelConverter periodoPagoModelConverter;

    @Autowired
    private PeriodoPagoEntityConverter periodoPagoEntityConverter;

    private Converter<Parametro, ParametroModel> parametroModelConverter = new Converter<Parametro, ParametroModel>() {

        @Override
        public ParametroModel convert(Parametro parametro) {
            return modelMapper.map(parametro, ParametroModel.class);
        }
    };

    private Converter<ParametroModel, Parametro> parametroEntityConverter = new Converter<ParametroModel, Parametro>() {

        @Override
        public Parametro convert(ParametroModel parametroModel) {
            return modelMapper.map(parametroModel, Parametro.class);
        }
    };

    private Converter<Festivo, FestivoModel> festivoModelConverter = new Converter<Festivo, FestivoModel>() {

        @Override
        public FestivoModel convert(Festivo festivo) {
            return modelMapper.map(festivo, FestivoModel.class);
        }
    };

    private Converter<FestivoModel, Festivo> festivoEntityConverter = new Converter<FestivoModel, Festivo>() {

        @Override
        public Festivo convert(FestivoModel festivoModel) {
            return modelMapper.map(festivoModel, Festivo.class);
        }
    };

    @Bean
    @Qualifier("customConversionService")
    public ConversionService customConversionService() {
        ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();

        converters.add(parametroModelConverter);
        converters.add(parametroEntityConverter);

        converters.add(festivoModelConverter);
        converters.add(festivoEntityConverter);

        converters.add(ubicacionModelConverter);
        converters.add(ubicacionEntityConverter);

        converters.add(periodoPagoModelConverter);
        converters.add(periodoPagoEntityConverter);

        factory.setConverters(converters);
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
