package com.titos.personmanagement.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 生成验证码的配置类
 * @author Titos
 */
@Configuration
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();

        /**
         * kaptcha.border 是否有图片边框
         */
        properties.setProperty(Constants.KAPTCHA_BORDER, "no");

        /**
         * kaptcha.border.color 边框颜色
         */
        properties.setProperty(Constants.KAPTCHA_BORDER_COLOR, "105,179,90");

        /**
         * kaptcha.textproducer.font.color 字体颜色
         */
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");

        /**
         * kaptcha.image.width 图片宽
         */
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "135");

        /**
         * kaptcha.image.height 图片高
         */
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "30");

        /**
         * kaptcha.textproducer.font.size 字体大小
         */
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "24");

        /**
         * kaptcha.session.key session key值
         */
        properties.setProperty(Constants.KAPTCHA_SESSION_KEY, "kaptchaCode");

        /**
         * kaptcha.textproducer.char.length 验证码长度
         */
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");

        /**
         * kaptcha.textproducer.char.string 使用那些字符生成验证码
         */
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "ABCDEFGHIJKMLNOPQRSTUVWXYZabcdefghijkmlopqrstuvwxyz0123456789");

        /**
         * kaptcha.textproducer.font.names 使用哪些字体
         */
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial");

        /**
         * kaptcha.noise.color 干扰线颜色
         */
        properties.setProperty(Constants.KAPTCHA_NOISE_COLOR, "gray");

        /**
         * kaptcha.obscurificator.impl 图片样式阴影
         * WaterRipple: 为图像添加水波纹效果
         * ShadowGimpy: 为图像上的文字添加阴影和两个噪点
         */
        properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
