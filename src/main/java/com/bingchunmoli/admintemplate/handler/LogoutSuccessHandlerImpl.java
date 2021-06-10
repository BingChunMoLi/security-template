package com.bingchunmoli.admintemplate.handler;

import com.bingchunmoli.admintemplate.domain.LoginUser;
import com.bingchunmoli.admintemplate.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author BingChunMoLi
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = jwtUtils.getLoginUser(request);
        System.out.println("loginUser:" + loginUser);
        if (loginUser != null){
            System.out.println(loginUser);
        }

        System.out.println("logout");
    }
}
