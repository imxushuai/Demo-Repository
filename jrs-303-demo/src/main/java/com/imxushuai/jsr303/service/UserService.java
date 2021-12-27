package com.imxushuai.jsr303.service;

import com.imxushuai.jsr303.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    public void save(User user) {
        log.info("用户新增成功.....");
    }

    public void update(User user) {
        log.info("用户更新成功.....");
    }
}
