package com.example.demo.deserializer;

import com.example.demo.util.ApplicationContextUtil;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;

@Data
@EqualsAndHashCode(callSuper = true)
public class ObjectInStringDeserializer extends JsonDeserializer<Object> implements ContextualDeserializer {

    private Class<?> targetClass;

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper objectMapper = ApplicationContextUtil.getBean(ObjectMapper.class);
        return objectMapper.readValue(jsonParser.getValueAsString(), targetClass);
    }


    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        String className = ctxt.getContextualType().toCanonical();
        try {
            targetClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }
}
