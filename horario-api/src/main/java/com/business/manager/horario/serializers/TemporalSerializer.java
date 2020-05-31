package com.business.manager.horario.serializers;

import com.business.manager.horario.util.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.temporal.Temporal;

public class TemporalSerializer extends StdSerializer<Temporal> {

    public TemporalSerializer() {
        this(null);
    }

    public TemporalSerializer(Class<Temporal> t) {
        super(t);
    }

    @Override
    public void serialize(
            Temporal temporal, JsonGenerator gen, SerializerProvider arg2)
            throws IOException {
        gen.writeString(DateUtil.getStringOf(temporal));
    }


}
