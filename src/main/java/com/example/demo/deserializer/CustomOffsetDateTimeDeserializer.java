package com.example.demo.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateTimeKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.OffsetDateTimeKeyDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.*;

@Slf4j
public class CustomOffsetDateTimeDeserializer extends StdDeserializer<OffsetDateTime> {


    public CustomOffsetDateTimeDeserializer() {
        super(OffsetDateTime.class);
    }

    @Override
    public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try{
//            log.info("jsonParser.getValueAsString() : {}",jsonParser.getValueAsString());
            return (OffsetDateTime) OffsetDateTimeKeyDeserializer.INSTANCE.deserializeKey(jsonParser.getValueAsString(),deserializationContext);
        }catch (Exception e){
            //log.info("fail to deserialize with normal way");
            try{
                return deserialize(jsonParser.getValueAsString(),deserializationContext);
            }catch (Exception ex){
                throw e;
            }
        }
    }

    public OffsetDateTime deserialize(String key, DeserializationContext ctxt) throws IOException {
        log.info("try to deserialize");
        ZoneId zone = ZoneId.of("Asia/Hong_Kong");
        try{
            LocalDateTime ldt = (LocalDateTime) LocalDateTimeKeyDeserializer.INSTANCE.deserializeKey(key,ctxt);
            return ldt.atZone(zone).toOffsetDateTime();
        }catch (Exception e){
            try {
                LocalDate ld = (LocalDate) LocalDateKeyDeserializer.INSTANCE.deserializeKey(key,ctxt);
                return ld.atTime(LocalTime.MIN).atZone(zone).toOffsetDateTime();
            } catch(Exception ex){
                log.info("fail to cast to localdate");
                throw ex;
            }
        }
    }
}
