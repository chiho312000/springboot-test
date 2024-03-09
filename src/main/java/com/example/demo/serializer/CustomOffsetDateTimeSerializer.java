package com.example.demo.serializer;

import com.example.demo.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.OffsetDateTime;

public class CustomOffsetDateTimeSerializer extends StdSerializer<OffsetDateTime> implements ContextualSerializer {

    private JsonFormat format;

    public CustomOffsetDateTimeSerializer() {
        super(OffsetDateTime.class);
    }

    @Override
    public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (format != null) {
            gen.writeString(DateTimeUtil.offsetDateTimeToIsoString(value, format.pattern()));
        } else {
            gen.writeString(DateTimeUtil.offsetDateTimeToIsoString(value));
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        format = beanProperty.getAnnotation(JsonFormat.class);
        return this;
    }
}
