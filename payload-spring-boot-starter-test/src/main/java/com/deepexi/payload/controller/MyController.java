package com.deepexi.payload.controller;

import com.deepexi.payload.annotation.Payload;
import com.deepexi.payload.exception.CustomerException;
import com.deepexi.payload.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Payload
@RequestMapping("/users")
@RestController
public class MyController {

    @GetMapping("/{id}")
    public User get(@PathVariable Integer id) {
        if (id == 1) {
            return new User(1, "张三", 18);
        }
        throw new CustomerException();
    }

}
