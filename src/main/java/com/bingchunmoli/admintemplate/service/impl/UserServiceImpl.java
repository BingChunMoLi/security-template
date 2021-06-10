package com.bingchunmoli.admintemplate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bingchunmoli.admintemplate.domain.LoginUser;
import com.bingchunmoli.admintemplate.domain.User;
import com.bingchunmoli.admintemplate.mapper.UserMapper;
import com.bingchunmoli.admintemplate.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author BingChunMoLi
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        Set<String> perms = new HashSet<String>();
        perms.add("*:*:*");
        return new LoginUser(user, perms);
    }
}




