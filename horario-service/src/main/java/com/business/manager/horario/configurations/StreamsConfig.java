package com.business.manager.horario.configurations;

import com.business.manager.horario.streams.HorarioInputStreams;
import com.business.manager.horario.streams.HorarioOutputStreams;

import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding({HorarioInputStreams.class, HorarioOutputStreams.class})
public class StreamsConfig {
}
