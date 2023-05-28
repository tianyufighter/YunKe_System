package com.titos.tool.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 生成token需要的信息类
 * @author Titos
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenContent {
    /**
     * payload的自定义变量
     */
    private CustomStatement customStatement;
    /**
     * 密钥
     */
    private String secretKey;
    /**
     * 有效时间
     */
    private int milliSecond = 7 * 1000 * 60 * 60 * 24;
    /**
     * 签发者
     */
    private String jwtIssuer = "YUNKE";
    /**
     * 受众
     */
    private String jwtAud = "user";

    public TokenContent(CustomStatement customStatement, String secretKey) {
        this.customStatement = customStatement;
        this.secretKey = secretKey;
    }
}
