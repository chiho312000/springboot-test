package com.example.demo.util;

import com.example.demo.deserializer.CustomInstantDeserializer;
import com.example.demo.deserializer.CustomOffsetDateTimeDeserializer;
import com.example.demo.introspector.CustomAnnotationIntrospector;
import com.example.demo.serializer.CustomInstantSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.OffsetDateTime;

@Slf4j
public class CommonUtil {

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                final String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            log.error("Error in hashing string", e);
        }
        return null;
    }

    public static Jackson2ObjectMapperBuilder getObjectMapperBuilder() {
        return getObjectMapperBuilder(false, false);
    }

    public static Jackson2ObjectMapperBuilder getObjectMapperBuilder(boolean isMask, boolean logConfidential) {
        //        JavaTimeModule module = new JavaTimeModule();
//        Jdk8Module module1 = new Jdk8Module();
//        JodaModule module2 = new JodaModule();
//        ParameterNamesModule module3 = new ParameterNamesModule();
//        JsonComponentModule module4 = new JsonComponentModule();
//        GeoJsonModule module5 = new GeoJsonModule();
//        GeoModule module6 = new GeoModule();

        SimpleModule module7 = new SimpleModule() {{
            addDeserializer(OffsetDateTime.class, new CustomOffsetDateTimeDeserializer());
            addDeserializer(Instant.class, new CustomInstantDeserializer());
            addSerializer(Instant.class, new CustomInstantSerializer());
        }};
        Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json()
                .modulesToInstall(module7)
//                .modules(module,module1,module2,module3,module4,module5,module6,module7)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//                .serializationInclusion(JsonInclude.Include.NON_EMPTY)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
                .featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
//                .visibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE)
                .timeZone("GMT+8")
                .annotationIntrospector(new CustomAnnotationIntrospector(isMask, logConfidential));
//                .visibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE)
//                .timeZone("GMT+8");

        return builder;
    }
}
