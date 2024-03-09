package com.example.demo.serializer;

import com.example.demo.util.ApplicationContextUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ObjectInStringSerializer extends StdSerializer<Object> {

    protected ObjectInStringSerializer() {
        super(Object.class);
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        ObjectMapper objectMapper = ApplicationContextUtil.getBean(ObjectMapper.class);
        gen.writeString(objectMapper.writeValueAsString(value));
    }
}
