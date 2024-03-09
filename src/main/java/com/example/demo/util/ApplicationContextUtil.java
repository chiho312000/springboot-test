package com.example.demo.util;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    @Getter
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clasz){
        return applicationContext.getBean(clasz);
    }


    public static String getProperty(String property) {
        return getProperty(property, String.class);
    }

    public static String getProperty(String property, String defaultValue) {
        return getProperty(property, String.class, defaultValue);
    }

    public static <T> T getProperty(String property, Class<T> tClass) {
        return applicationContext.getEnvironment().getProperty(property, tClass);
    }

    public static <T> T getProperty(String property, Class<T> tClass, T defaultValue) {
        return applicationContext.getEnvironment().getProperty(property, tClass, defaultValue);
    }

    public static <T> T getProperty(String property, ParameterizedTypeReference<T> tClass) {
        return (T) applicationContext.getEnvironment().getProperty(property, ResolvableType.forType(tClass).resolve());
    }
}
