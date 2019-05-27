package cc.ithinkyou.controller;

import cc.ithinkyou.model.Result;
import cc.ithinkyou.model.User;
import cc.ithinkyou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
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
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用户列表
     *
     * @return
     */
    @RequestMapping("/user/list")
    public Mono<Result> getUsers() {
        return Mono.just(new Result(userService.getAll()));
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @PostMapping("/user/add")
    public Mono<Result> addUser(@RequestBody User user) {
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        }
        user.setCreated(new Date());
        userService.save(user);
        return Mono.just(new Result());
    }
}
