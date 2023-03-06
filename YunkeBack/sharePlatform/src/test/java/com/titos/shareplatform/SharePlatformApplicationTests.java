package com.titos.shareplatform;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SharePlatformApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void t(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePwd = passwordEncoder.encode("kurihada");
        System.out.println(encodePwd);
    }

}
