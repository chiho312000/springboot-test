package com.example.demo.model.repository;

import com.example.demo.annotation.Mask;
import com.example.demo.model.common.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document("User")
public class User extends BaseMongoObject {

    @BsonId
    private ObjectId _id;
    
    private String name;
    private CommonConstant.GENDER gender;

    @Indexed(unique = true)
    private String email;

    @Mask
    private String passwordHash;

}
