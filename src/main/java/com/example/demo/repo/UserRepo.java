package com.example.demo.repo;

import com.example.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepo extends MongoRepository<User, String> {
    @Query("{ 'id' : ?0 }")
    User findByUserId(String id);
}
