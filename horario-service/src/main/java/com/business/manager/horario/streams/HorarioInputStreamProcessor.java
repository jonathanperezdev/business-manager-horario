package com.business.manager.horario.streams;

import com.business.manager.horario.model.streams.EmpleadoStreamModel;
import com.business.manager.horario.model.streams.UbicacionStreamModel;
import com.business.manager.horario.services.EmpleadoService;
import com.business.manager.horario.services.UbicacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class HorarioInputStreamProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(HorarioInputStreamProcessor.class);

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private UbicacionService ubicacionService;

    @StreamListener(HorarioInputStreams.EMPLEADO_INPUT)
    public void handleEmpleadoStream(@Payload EmpleadoStreamModel empleadoStream) {
        LOGGER.info("Received empleado stream: {}", empleadoStream);
        switch (empleadoStream.getAction()){
            case UPSERT:
                empleadoService.upsertEmpleado(empleadoStream.getEmpleado());
                break;
            case DELETE:
                empleadoService.deleteEmpleado(empleadoStream.getEmpleado().getId());
                break;
        }
    }

    @StreamListener(HorarioInputStreams.UBICACION_INPUT)
    public void handleUbicacionStream(@Payload UbicacionStreamModel ubicacionStreamModel) {
        LOGGER.info("Received ubicacion stream: {}", ubicacionStreamModel);
        switch (ubicacionStreamModel.getAction()){
            case UPSERT:
                ubicacionService.updateUbicacion(ubicacionStreamModel.getUbicacion());
                break;
            case DELETE:
                ubicacionService.deleteUbicacion(ubicacionStreamModel.getUbicacion().getId());
                break;
        }
    }
}
