package com.example.demo.model.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseMongoObject {

    @CreatedDate
    protected OffsetDateTime createdAt;

    @LastModifiedDate
    protected OffsetDateTime updatedAt;
}
