package com.bingchunmoli.admintemplate.config.kaptcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static com.google.code.kaptcha.Constants.*;

@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha defaultKaptcha(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        //边框
        properties.setProperty(KAPTCHA_BORDER, "yes");
        //颜色
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
        //宽度
        properties.setProperty(KAPTCHA_IMAGE_WIDTH, "160");
        //高度
        properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "60");
        //字体大小
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, "38");
        //key
        properties.setProperty(KAPTCHA_SESSION_CONFIG_KEY, "kaptchaCode");
        //字符长度
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        //文字样式
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier");
        //图片样式
        properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
