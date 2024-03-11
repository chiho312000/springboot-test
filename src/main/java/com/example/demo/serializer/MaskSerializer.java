package com.example.demo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Collection;

@Slf4j
public class MaskSerializer extends StdSerializer<Object> {
    public MaskSerializer() {
        super(Object.class);
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        String mask = "";

        if (value == null) {
            mask = null;
        } else if (value.getClass() == String.class) {
            mask = (String) value;
            mask = "Masked length=" + mask.length();
        } else if (value.getClass().isArray()) {
            mask = "Masked array length=" + Array.getLength(value);
        } else if (value instanceof Collection<?> collection) {
            mask = "Masked collection length=" + collection.size();
        } else {
            mask = "Masked length=" + value.toString().length();
        }

        gen.writeString(mask);
    }
}
