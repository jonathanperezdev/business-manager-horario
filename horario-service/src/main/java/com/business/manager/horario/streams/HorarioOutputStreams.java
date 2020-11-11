package com.business.manager.horario.streams;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface HorarioOutputStreams {
    String PARAMETRO_OUTPUT = "parametros-out";
    @Output(PARAMETRO_OUTPUT)
    MessageChannel outboundParametros();
}
