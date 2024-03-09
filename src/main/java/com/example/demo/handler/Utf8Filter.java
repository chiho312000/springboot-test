package com.example.demo.handler;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class Utf8Filter extends Filter<ILoggingEvent> {
    @SneakyThrows
    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        try{

            CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();
            encoder.encode(CharBuffer.wrap(iLoggingEvent.getFormattedMessage().toCharArray()));
        }catch(Exception e){
            log.info("it is not utf8");
            return FilterReply.DENY;
        }
        return FilterReply.ACCEPT;
    }
}
