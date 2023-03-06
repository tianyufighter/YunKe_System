package com.titos.tool.token;

import com.titos.tool.exception.JwtExpireException;
import com.titos.tool.exception.JwtNotExistException;
import com.titos.tool.exception.JwtVerifyException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * token工具类
 * @author Titos
 */
@Component
public class TokenUtil {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String ID = "id";
    public static final String ROLE = "role";
    public static final String USERNAME = "username";
    /**
     * 统计在线人数
     */
    public static Map countMap = new ConcurrentHashMap<String, Object>();

    /**
     * 私有方法防止构造
     */
    private TokenUtil(){}

    /**
     * 生成token
     * @param tokenContent 生成token需要用到的信息
     * @return token
     */
    public static String buildToken(TokenContent tokenContent) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", tokenContent.getCustomStatement().getId());
        claims.put("role", tokenContent.getCustomStatement().getRole());
        claims.put("username", tokenContent.getCustomStatement().getUsername());
        // 获取当前的时间
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(Calendar.MILLISECOND, tokenContent.getMilliSecond());
        // token过期时间
        Date expireDate = calendar.getTime();
        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .setIssuer(tokenContent.getJwtIssuer())
                .setAudience(tokenContent.getJwtAud())
                .signWith(SignatureAlgorithm.HS256, tokenContent.getSecretKey())
                .compact();
        return jwtToken;
    }

    /**
     * 从toke中获取用户的自定义变量信息
     * @param request http请求
     * @param secretKey 密钥
     * @return 用户在token中的自定义信息
     */
    public static CustomStatement getMsgFromToken(HttpServletRequest request, String secretKey) throws JwtExpireException, JwtVerifyException , JwtNotExistException{
        String token = getToken(request);
        CustomStatement customStatement = new CustomStatement();
        customStatement.setId((Integer) getTokenValueByKey(token, secretKey, ID));
        customStatement.setRole((Integer) getTokenValueByKey(token, secretKey, ROLE));
        customStatement.setUsername((String) getTokenValueByKey(token, secretKey, USERNAME));
        return customStatement;
    }

    /**
     * 从toke中获取用户的自定义变量信息
     * @param token token值
     * @param secretKey 密钥
     * @return 用户在token中的自定义信息
     */
    public static CustomStatement getMsgFromToken(String token, String secretKey) throws JwtExpireException, JwtVerifyException , JwtNotExistException{
        CustomStatement customStatement = new CustomStatement();
        customStatement.setId((Integer) getTokenValueByKey(token, secretKey, ID));
        customStatement.setRole((Integer) getTokenValueByKey(token, secretKey, ROLE));
        customStatement.setUsername((String) getTokenValueByKey(token, secretKey, USERNAME));
        return customStatement;
    }

    /**
     * 根据Token中的键获取对应的值
     * @param jwt token
     * @param secretKey 密钥
     * @param key 键
     * @return 值
     */
    public static Object getTokenValueByKey(String jwt, String secretKey, String key) {
        Claims claims = parse(jwt, secretKey);
        if (claims == null || claims.get(key) == null) {
            return null;
        }
        return claims.get(key);
    }

    /**
     * 将token字符串解析为Jws对象，然后获取claims
     * @param jwt 前端头部的携带的token值
     * @param secretKey 密钥
     * @return
     */
    public static Claims parse(String jwt, String secretKey) throws JwtExpireException, JwtVerifyException , JwtNotExistException  {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException expiredJwtException) {
            throw new JwtExpireException("token过期");
        } catch (JwtException jwtException) {
            throw new JwtVerifyException("JWT验证错误");
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new JwtNotExistException("Token为空");
        }
        // 解析token的签发时间
//        Date issuedAt = claims.getIssuedAt();
        // 以签发时间为key，当前时间+60s为value存入countMap中
        countMap.put(claims.get("username"), System.currentTimeMillis() + 60 * 1000);
        return claims;
    }

    /**
     * 从http请求的Cookie中获取token
     * @param request http请求
     * @return token值
     */
    public static String getToken(HttpServletRequest request) {
        String token =request.getHeader(TOKEN_HEADER);
        if (token == null) {
            throw new JwtNotExistException("Token不存在");
        }
        return token;
    }

    public static boolean isTokenExisted(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        if(token == null) {
            return false;
        }
        return true;
    }

    /**
     * 获取在线人数
     * @return 在线人数
     */
    public static Integer getOnlineCount() {
        int onlineCount = 0;
        // 获取countMap的迭代器
        Iterator iterator = countMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            Long value = (Long) entry.getValue();
            if (value > System.currentTimeMillis()) {
                // 过期时间大于当前时间则没有过期
                onlineCount++;
            } else {
                iterator.remove();
            }
        }
        return onlineCount;
    }
}
