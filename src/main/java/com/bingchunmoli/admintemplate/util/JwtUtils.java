package com.bingchunmoli.admintemplate.util;

import cn.hutool.core.util.StrUtil;
import com.bingchunmoli.admintemplate.domain.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author BingChunMoLi
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtUtils {
    /**
     * 请求头
     */
    private String header;
    /**
     * 请求头前缀
     */
    private String tokenStart;
    /**
     * 密钥
     */
    private String secret;
    /**
     * 令牌有效期
     */
    private Integer expireTime;
    /**
     * 签发者
     */
    private String iss;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public String createJwt(String userName) {
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("login_user_key", userName);
        return Jwts.builder()
                .setClaims(map)
                .setId(UUID.randomUUID().toString())
                .setIssuer(iss)
                .setAudience(userName)
                .setSubject(userName)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public void verifyToken(LoginUser user) {
        Long expireTime = user.getJtime();
        Long currenTime = System.currentTimeMillis();
        if (expireTime - currenTime <= 1000L * 60 * 20) {
            refareshToken(user);
        }
    }

    private void refareshToken(LoginUser user) {
        user.setTime(System.currentTimeMillis());
        user.setJtime(user.getTime() + expireTime * 60 * 1000);
        String tokenKey = getTokenKey(user.getToken());
        redisTemplate.opsForValue().set(tokenKey, user, expireTime, TimeUnit.MINUTES);
    }

    private String getTokenKey(String k) {
        return "login:" + k;
    }


    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        if (StrUtil.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            String uuid = String.valueOf(claims.get("login"));
            String useKey = getTokenKey(uuid);
            Object obj = redisTemplate.opsForValue().get(useKey);
            System.out.println(obj);
            LoginUser user = (LoginUser) obj;
            return user;
        }
        return null;
    }

    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StrUtil.isNotEmpty(token)&& token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
        }
        System.out.println("token: "+token);
        return token;
    }

    public String getUsernameFromToken(String token) {
        return parseToken(token).getSubject();
    }

    public String createToken(LoginUser loginUser) {
        String token = UUID.randomUUID().toString();
        loginUser.setToken(token);
        //设置用户浏览器，系统等信息
        refareshToken(loginUser);
        Map<String, Object> map = new HashMap<>(1);
        map.put("login", token);
        return createToken(map,loginUser.getUsername());
    }

    private String createToken(Map<String,Object> claims,String username){
        return Jwts.builder()
                .setClaims(claims)
                .setId(String.valueOf(claims.get("login")))
                .setIssuer(iss)
                .setIssuedAt(new Date())
                .setAudience(username)
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}
