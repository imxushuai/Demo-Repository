package com.imxushuai.mongodb.springdata;

import com.imxushuai.mongodb.pojo.User;
import com.imxushuai.mongodb.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * 使用Spring Data MongoDB操作MongoDB
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringDataMongoDB {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void createCollection() {
        mongoTemplate.createCollection(User.class);
    }

    @Test
    public void createDocument() {
        User user = new User(null, "xushuai 3", "19", "BeiJing");
        userRepository.insert(user);
    }

    @Test
    public void findAllByDocument() {
        List<User> userList = userRepository.findAll();
        userList.forEach(user -> System.out.println(user.toString()));
    }

    @Test
    public void findByParams() {
        List<User> byAddressLike = userRepository.findByAddressLike("Bei");
        byAddressLike.forEach(user -> System.out.println(user.toString()));
    }

    @Test
    public void update() {
        Optional<User> optionalUser = userRepository.findById("5d07a7330a6c462c5822adf0");
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName("Jerry");
            userRepository.save(user);
        }
    }

    @Test
    public void delete() {
        userRepository.deleteById("5d07a7330a6c462c5822adf0");
    }
}
