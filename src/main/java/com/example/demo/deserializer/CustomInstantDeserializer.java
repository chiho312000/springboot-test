package com.example.demo.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

@Slf4j
public class CustomInstantDeserializer extends StdDeserializer<Instant> {

    public CustomInstantDeserializer() {
        super(Instant.class);
    }

    @Override
    public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String dateStr = jsonParser.getValueAsString();
        try {
            double timestamp = Double.parseDouble(dateStr);
            return Instant.ofEpochSecond((long) timestamp);
        } catch (Exception ignored) {
        }
        try {
            return Instant.parse(jsonParser.getValueAsString());
        } catch (Exception e) {
            try {
                return Instant.from((new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]XXX")
                        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                        .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
                        .toFormatter().withZone(ZoneId.of("Asia/Hong_Kong"))).parse(dateStr));
            } catch (Exception ex) {
                log.warn("fail to deserialize to offset date time in normal way");
                CustomOffsetDateTimeDeserializer deserializer = new CustomOffsetDateTimeDeserializer();
                OffsetDateTime dateTime = deserializer.deserialize(dateStr, deserializationContext);
                return dateTime.toInstant();
            }
        }
    }
}
