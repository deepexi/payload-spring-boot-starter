package com.deepexi.payload.controller;

import com.deepexi.payload.annotation.Payload;
import com.deepexi.payload.exception.CustomerException;
import com.deepexi.payload.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Payload
@RequestMapping("/users")
@RestController
public class MyController {

    @GetMapping("/")
    public User get() {
        throw new CustomerException();
//        return new User(1, "张三", 18);
    }

}
