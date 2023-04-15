package com.titos.tool.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Titos
 */
@RefreshScope
@Component
public class TokenSettings {
    @Value("${token.secretKey:YUNKE}")
    private String tokenSecretKey;
    public String getTokenSecretKey() {
        return tokenSecretKey;
    }
}
