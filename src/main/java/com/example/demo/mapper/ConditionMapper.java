package com.example.demo.mapper;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;

@Mapper
public interface ConditionMapper {

    @Condition
    default boolean isNotNull(Object obj) {
        return obj != null;
    }
}
