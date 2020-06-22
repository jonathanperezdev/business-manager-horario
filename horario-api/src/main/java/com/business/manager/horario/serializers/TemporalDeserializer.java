package com.business.manager.horario.serializers;

import com.business.manager.horario.util.DateUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.temporal.Temporal;

public class TemporalDeserializer extends StdDeserializer<Temporal> {

    public TemporalDeserializer() {
        this(null);
    }

    public TemporalDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Temporal deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
        return DateUtil.getTemporalOf(jsonparser.getText());
    }
}
