package com.bingchunmoli.admintemplate.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bingchunmoli.admintemplate.domain.LoginBody;
import com.bingchunmoli.admintemplate.domain.User;
import com.bingchunmoli.admintemplate.service.UserService;
import com.bingchunmoli.admintemplate.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author BingChunMoLi
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoginServiceImpl loginService;

    @RequestMapping("list")
    public List<User> list(){
        return userService.list();
    }

    @RequestMapping("register")
    public boolean register(User user){
        if (userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, user.getUsername())) != null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.save(user);
    }

    @PostMapping("login")
    public String login(@RequestBody LoginBody loginBody){
        return loginService.login(loginBody.getUsername(),loginBody.getPassword(),loginBody.getCode(),loginBody.getUuid());
    }
}
