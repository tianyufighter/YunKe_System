package com.titos.technicalarchive;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechnicalArchiveApplication.class)
class TechnicalArchiveApplicationTests {
    @Test
    void contextLoads() {
        System.out.println(1);
    }

}
