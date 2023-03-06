package com.titos.tool;

import com.titos.tool.sensitive.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ToolApplicationTests {
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Test
    void contextLoads() {
        System.out.println(sensitiveFilter.filter(""));
    }

}
