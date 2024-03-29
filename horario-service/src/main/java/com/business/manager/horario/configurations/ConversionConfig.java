package com.business.manager.horario.configurations;

import com.business.manager.horario.converters.FestivoEntityConverter;
import com.business.manager.horario.converters.FestivoModelConverter;
import com.business.manager.horario.converters.empleado.EmpleadoEntityConverter;
import com.business.manager.horario.converters.empleado.EmpleadoModelConverter;
import com.business.manager.horario.converters.empleado.HorarioEmpleadoModelConverter;
import com.business.manager.horario.converters.empleado.PeriodoPagoEntityConverter;
import com.business.manager.horario.converters.empleado.PeriodoPagoModelConverter;
import com.business.manager.horario.converters.ubicacion.UbicacionEntityConverter;
import com.business.manager.horario.converters.ubicacion.UbicacionModelConverter;
import com.business.manager.horario.dao.entities.Parametro;
import com.business.manager.horario.model.ParametroModel;
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

    @Autowired
    private FestivoModelConverter festivoModelConverter;

    @Autowired
    private HorarioEmpleadoModelConverter horarioEmpleadoModelConverter;

    @Autowired
    private FestivoEntityConverter festivoEntityConverter;

    @Autowired
    private EmpleadoModelConverter empleadoModelConverter;

    @Autowired
    private EmpleadoEntityConverter empleadoEntityConverter;

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

        converters.add(horarioEmpleadoModelConverter);

        converters.add(empleadoModelConverter);
        converters.add(empleadoEntityConverter);

        factory.setConverters(converters);
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
