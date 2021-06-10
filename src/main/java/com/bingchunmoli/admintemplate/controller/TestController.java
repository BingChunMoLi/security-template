package com.bingchunmoli.admintemplate.controller;

import com.bingchunmoli.admintemplate.util.JwtUtils;
import com.google.code.kaptcha.Producer;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.bingchunmoli.admintemplate.constant.SystemConstant.KAPTCHA_CODE;

/**
 * @author BingChunMoLi
 */
@RestController
public class TestController {

    @Autowired
    private JwtUtils jwtUtils;

    @RequestMapping("createToken")
    public String test() {
        return jwtUtils.createJwt("username");
    }

    @Autowired
    Producer producer;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @GetMapping(value = "kaptcha", produces = MediaType.IMAGE_JPEG_VALUE)
    public BufferedImage bufferedImage() {
        String s = UUID.randomUUID().toString();
        String text = producer.createText();
        redisTemplate.opsForValue().set(KAPTCHA_CODE + s, text, 2, TimeUnit.MINUTES);
        return producer.createImage(text);
    }


    @RequestMapping("claims")
    public Claims getClaims(){
        return jwtUtils.parseToken(test());
    }

    @RequestMapping("username")
    public String getUsername() {
        return jwtUtils.getUsernameFromToken(test());
    }
}
