package com.ithinkyou.cc.controller;

import com.ithinkyou.cc.mapper.UserMapper;
import com.ithinkyou.cc.model.Result;
import com.ithinkyou.cc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * 用户控制器
 *
 * @author GL
 * @created 2018/5/21.
 */
@RestController
public class UserCtrl {

    @Autowired
    UserMapper userMapper;

    /**
     * 用户列表
     *
     * @return
     */
    @RequestMapping("/user/list")
    public Mono<Result> getUsers() {
        return Mono.just(new Result(userMapper.selectAll()));
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @RequestMapping("/user/add")
    public Mono<Result> addUser(@RequestBody User user) {
        user.setPassword("e10adc3949ba59abbe56e057f20f883e");
        user.setCreated(new Date());
        userMapper.insert(user);
        return Mono.just(new Result());
    }
}
