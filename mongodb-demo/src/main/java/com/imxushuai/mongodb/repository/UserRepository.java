package com.imxushuai.mongodb.repository;

import com.imxushuai.mongodb.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByAddressLike(String address);

    List<User> findByNameLike(String name);
}
