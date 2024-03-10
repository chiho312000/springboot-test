package com.example.demo.converter;

import com.example.demo.util.DateTimeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.Decimal128;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Slf4j
public class MongoConverter {

    public interface MongoConverterInterface extends GenericConverter {
//        dummy interface for identifying custom converters
    }



    @WritingConverter
    @Component
    public static class OffsetDateTimeSerializer implements MongoConverterInterface {

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Set.of(new ConvertiblePair(OffsetDateTime.class, Date.class), new ConvertiblePair(OffsetDateTime.class, LocalDateTime.class));
        }

        @Override
        public Object convert(Object source, @NonNull TypeDescriptor sourceType, @NonNull TypeDescriptor targetType) {
            return Date.from(((OffsetDateTime) source).toInstant());
        }
    }

    @WritingConverter
    @Component
    public static class EnumSerializer extends MongoConverter implements MongoConverterInterface {
        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(new ConvertiblePair(Enum.class, String.class));
        }

        @Override
        public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            return source == null ? null : source.toString();
        }
    }

    @ReadingConverter
    @Component
    public static class OffsetDateTimeDeserializer implements MongoConverterInterface {

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Set.of(new ConvertiblePair(Date.class, OffsetDateTime.class), new ConvertiblePair(LocalDateTime.class, OffsetDateTime.class));
        }

        @Override
        public Object convert(Object source, @NonNull TypeDescriptor sourceType, @NonNull TypeDescriptor targetType) {
            Instant instant = null;
            if (source instanceof LocalDateTime d) {
                instant = d.toInstant(ZoneOffset.from(OffsetDateTime.now()));
            } else if (source instanceof Date d) {
                instant = d.toInstant();
            }
            if (instant == null) return null;
            return OffsetDateTime.ofInstant(instant, DateTimeUtil.DEFAULT_ZONE);
        }
    }

    @ReadingConverter
    @Component
    public static class EnumDeserializer implements MongoConverterInterface {

        @Autowired
        private ObjectMapper mapper;
        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(new ConvertiblePair(String.class, Enum.class));
        }

        @Override
        public Enum<?> convert(Object source, @NonNull TypeDescriptor sourceType, @NonNull TypeDescriptor targetType) {
            try {
                String target = (String) source;
                if(source instanceof String){
                    target = StringUtils.upperCase(StringUtils.replace(target," ","_"));
                }
                return mapper.convertValue(target, targetType.getType().asSubclass(Enum.class));
            } catch (Exception e) {
                return null;
            }
        }
    }

    @ReadingConverter
    @Component
    public static class TemporalUnitDeserializer implements MongoConverterInterface {
        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(new ConvertiblePair(String.class, TemporalUnit.class));
        }

        @Override
        public TemporalUnit convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            try {
                if(source != null){
                    return ChronoUnit.valueOf(((String) source).toUpperCase());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @WritingConverter
    @Component
    public static class BigDecimalToDecimal128Converter implements MongoConverterInterface {

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(new ConvertiblePair(BigDecimal.class, Decimal128.class));
        }

        @Override
        public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            if(source != null)
                return new Decimal128 ((BigDecimal)source);
            return null;
        }
    }

    @ReadingConverter
    @Component
    public static class Decimal128Deserializer implements MongoConverterInterface {
        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(new ConvertiblePair(Decimal128.class, BigDecimal.class));
        }

        @Override
        public BigDecimal convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            if(source != null)
                return ((Decimal128) source).bigDecimalValue();
            return null;
        }
    }
}
