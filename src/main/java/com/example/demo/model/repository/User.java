package com.example.demo.model.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document("User")
public class User {

    @BsonId
    private ObjectId _id;

    private String name;
    private String gender;
    private String email;

}
