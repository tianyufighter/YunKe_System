package com.titos.conversation;

import com.titos.tool.token.CustomStatement;
import com.titos.tool.token.TokenContent;
import com.titos.tool.token.TokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConversationApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConversationApplicationTests {

    @Test
    public void test01() {
        CustomStatement customStatement = new CustomStatement();
        customStatement.setId(1);
        customStatement.setUsername("kurihada");
        customStatement.setRole(3);
        TokenContent tokenContent = new TokenContent(customStatement, "YUNKE");
        String token = TokenUtil.buildToken(tokenContent);
        System.out.println(token);
    }

    @Test
    public void test02() {
        CustomStatement customStatement = new CustomStatement();
        customStatement.setId(13);
        customStatement.setUsername("titos");
        customStatement.setRole(1);
        TokenContent tokenContent = new TokenContent(customStatement, "YUNKE");
        String token = TokenUtil.buildToken(tokenContent);
        System.out.println(token);
    }

//    @Test
//    public void test03() {
//        CustomStatement customStatement = new CustomStatement();
//        customStatement.setId(10003);
//        customStatement.setUsername("ddgo19");
//        customStatement.setRole(1);
//        TokenContent tokenContent = new TokenContent(customStatement, "YUNKE");
//        String token = TokenUtil.buildToken(tokenContent);
//        System.out.println(token);
//    }

}
