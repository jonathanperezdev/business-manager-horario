package com.business.manager.horario.streams;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface HorarioInputStreams {
    String EMPLEADO_INPUT = "empleados-in";
    @Input(EMPLEADO_INPUT)
    SubscribableChannel inboundEmpleados();

    String UBICACION_INPUT = "ubicaciones-in";
    @Input(UBICACION_INPUT)
    SubscribableChannel inboundUbicaciones();
}
