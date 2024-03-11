package com.example.demo.conf;

import com.example.demo.converter.OffsetDateTimeConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    private ObjectMapper mapper;
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new OffsetDateTimeConverter());
    }

    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(mapper);
    }

    @Override
    protected void configureMessageConverters(@NonNull List<HttpMessageConverter<?>> converters) {
        addDefaultHttpMessageConverters(converters);
        converters.removeIf(h -> h instanceof MappingJackson2HttpMessageConverter);
        converters.add(mappingJackson2HttpMessageConverter());
        super.configureMessageConverters(converters);
    }
}
