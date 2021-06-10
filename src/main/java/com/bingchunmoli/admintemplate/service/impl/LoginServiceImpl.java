package com.bingchunmoli.admintemplate.service.impl;

import com.bingchunmoli.admintemplate.domain.LoginUser;
import com.bingchunmoli.admintemplate.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author BingChunMoLi
 */
@Service
public class LoginServiceImpl {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    public String login(String username, String password, String code, String uuid) {
        String captchaKey = "captcha:" + uuid;
        String captcha = "redis获取";
        //redis删除
        //判断是否为空，异步记录
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (Exception e){
            e.printStackTrace();
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return jwtUtils.createToken(loginUser);
    }
}
