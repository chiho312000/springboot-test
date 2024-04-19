package com.example.demo.model.mongoRepository;

import com.example.demo.model.repository.User;
import com.example.demo.model.request.LoginRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{ email: ?0, passwordHash: ?1 }")
    User getLoginUser(String email, String passwordHash);
}
