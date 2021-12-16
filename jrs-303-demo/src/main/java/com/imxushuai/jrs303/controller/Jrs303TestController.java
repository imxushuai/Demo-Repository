package com.imxushuai.jrs303.controller;

import com.imxushuai.jrs303.entity.CustomerResult;
import com.imxushuai.jrs303.entity.User;
import com.imxushuai.jrs303.service.UserService;
import com.imxushuai.jrs303.valid.AddGroup;
import com.imxushuai.jrs303.valid.UpdateGroup;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/user")
public class Jrs303TestController {

    @Autowired
    private UserService userService;

    @PostMapping("save")
    public ResponseEntity<CustomerResult> save(/*@Valid*/@Validated({AddGroup.class}) @RequestBody User user/*, BindingResult result*/) {
        /*if (StringUtils.isBlank(user.getName())) {
            return ResponseEntity.ok().body(CustomerResult.result(400, "用户姓名不能为空!"));
        }
        if (user.getAge() == null || user.getAge() < 0) {
            return ResponseEntity.ok().body(CustomerResult.result(400, "用户年龄不能为空且不能小于0!"));
        }*/
        /*if (result.hasErrors()) {
            Map<String, String> map = new HashMap<>();
            //1.获取错误的校验结果
            result.getFieldErrors().forEach((item) -> {
                //获取发生错误时的message
                String message = item.getDefaultMessage();
                //获取发生错误的字段
                String field = item.getField();
                map.put(field, message);
            });
            return ResponseEntity.ok(CustomerResult.result(400, "新增失败", map));
        }*/
        // 保存数据
        userService.save(user);

        return ResponseEntity.ok(CustomerResult.result(200, "新增成功"));
    }

    @PostMapping("update")
    public ResponseEntity<CustomerResult> update(/*@Valid*/@Validated({UpdateGroup.class}) @RequestBody User user) {
        // 更新数据
        userService.update(user);

        return ResponseEntity.ok().build();
    }

}
