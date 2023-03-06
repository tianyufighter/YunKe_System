package com.titos.tool.aop.token;

import com.titos.tool.annotions.InjectToken;
import com.titos.tool.token.CustomStatement;
import com.titos.tool.token.TokenContent;
import com.titos.tool.token.TokenSettings;
import com.titos.tool.token.TokenUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Titos
 */
@Aspect
@Component
public class TokenAspect {

    @Autowired
    private TokenSettings tokenSettings;

    @Around("@annotation(injectToken)")
    public Object autoInsertToken(ProceedingJoinPoint proceedingJoinPoint, InjectToken injectToken) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Object[] os = proceedingJoinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Class[] classes = methodSignature.getParameterTypes();
        boolean isInject = false;
        // Token中用户的相关信息
        CustomStatement customStatement = TokenUtil.getMsgFromToken(request, tokenSettings.getTokenSecretKey());
        for (int i = 0; i < classes.length; i++) {
            if (classes[i].equals(CustomStatement.class)) {
                os[i] = customStatement;
                isInject = true;
                break;
            }
        }
        if (TokenUtil.countMap.containsKey(customStatement.getUsername())) {
            TokenUtil.countMap.remove(customStatement.getUsername());
        }
        // 生成新的token
        TokenContent tokenContent = new TokenContent(customStatement, tokenSettings.getTokenSecretKey());
        String newToken = TokenUtil.buildToken(tokenContent);
        response.setHeader("Authorization", newToken);
        if (!isInject) {
            // 主要是用于统计在线人数
            TokenUtil.getMsgFromToken(request, tokenSettings.getTokenSecretKey());
        }
        return proceedingJoinPoint.proceed(os);
    }
}
