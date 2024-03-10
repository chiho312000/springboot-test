package com.example.demo.conf;

import com.example.demo.util.CommonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.jsr310.InstantCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
public class AppConfig {

    @Value("${server.tomcat.accesslog.pattern}")
    private String pattern;

    @Value("${logging.show.confidential}")
    private boolean logConfidential;
    @Bean
    public InstantCodec instantCodec() {
        return new InstantCodec();
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return CommonUtil.getObjectMapperBuilder().build();
    }

    @Bean(value = "maskedMapper")
    public ObjectMapper maskedObjectMapper() {
        return CommonUtil.getObjectMapperBuilder(true, logConfidential).build();
    }
}
