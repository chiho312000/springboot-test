package com.example.demo.converter;

import com.example.demo.util.DateTimeUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@Slf4j
public class OffsetDateTimeConverter implements Converter<String, OffsetDateTime> {


    @Override
    public OffsetDateTime convert(@NonNull String s) {
        if (StringUtils.isBlank(s))
            return null;
        try {
            return OffsetDateTime.parse(s, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (Exception e) {
            try {
                TemporalAccessor temporalAccessor = DateTimeUtil.dateTimeFormatter.parseBest(s, OffsetDateTime::from, LocalDateTime::from);
                if (temporalAccessor instanceof OffsetDateTime zonedDateTime) {
                    return zonedDateTime.atZoneSimilarLocal(DateTimeUtil.DEFAULT_ZONE).toOffsetDateTime();
                } else if (temporalAccessor instanceof LocalDateTime localDateTime) {
                    return localDateTime.atZone(DateTimeUtil.DEFAULT_ZONE).toOffsetDateTime();
                }
            } catch (Exception ignored) {

            }
        }
        return null;
    }
}
