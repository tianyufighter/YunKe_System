package com.titos.tool.aop.verify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.titos.info.exception.ParameterException;
import com.titos.tool.annotions.ParamVerify;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;


/**
 * @author Titos
 */
@Aspect
@Component
public class VerifyAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyAspect.class);

    public VerifyAspect() {
        LOGGER.info("Auto Verify Inject Start");
    }

    /**
     * @throws ParameterException 所有不合格的参数都将抛出该异常
     * @param joinPoint
     * @param verify
     */
    @Before("@annotation(verify)")
    public void verify(JoinPoint joinPoint, ParamVerify verify) {
        Object[] values = joinPoint.getArgs();
        if (values.length != 0) {
            String[] names = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
            if (names == null) {
                throw new ParameterException("This plug-in is based on cglib proxy. Please set the proxy to cglib proxy.\n<aop:aspectj-autoproxy proxy-target-class=\"true\"/>");
            } else {
                JSONObject data = new JSONObject();
                //KV组成
                for(int i = 0; i < names.length; ++i) {
                    if (!(values[i] instanceof HttpServletRequest) && !(values[i] instanceof HttpServletResponse)) {
                        data.put(names[i], values[i]);
                    }
                }
                /**
                 * 将对象重新使用JsonObject包装，这样每个对象内部的参数就可以成为节点
                 */
                String json = JSON.toJSONString(data);
                data = (JSONObject)JSON.parseObject(json, JSONObject.class);
                String[] notBlank = verify.notNull();
                if (!this.allNotNull(notBlank, values, names)) {
                    //逐个对参数进行校验
                    for(int i = 0; i < notBlank.length; ++i) {
                        String checkedKey = notBlank[i];
                        checkedKey = checkedKey.trim();
                        this.checkNull(checkedKey, data,verify.strictCollection());
                    }
                }
                //检查边界
                String[] strSizeLimit = verify.sizeLimit();
                for(int i = 0; i < strSizeLimit.length; ++i) {
                    String pattern = strSizeLimit[i];
                    pattern = this.transform(pattern);
                    SizeLimit limit = this.resolvePattern(pattern);
                    this.checkSize(limit, data);
                }

                String[] numberLimit = verify.numberLimit();
                for(int i = 0; i < numberLimit.length; ++i) {
                    String pattern = numberLimit[i];
                    pattern = this.transform(pattern);
                    SizeLimit limit = this.resolvePattern(pattern);
                    this.checkNumberLimit(limit, data);
                }

            }
        }
    }

    /**
     * String为 * 的情况，对所有参数进行校验
     * 由于使用了trim符号，*前后可以加空格等空白符号
     * @param notBlank
     * @param values
     * @param names
     * @return 若为*，检测并返回true,否则返回false
     */
    private boolean allNotNull(String[] notBlank, Object[] values, String[] names) {
        if (notBlank.length == 1 && "*".equals(notBlank[0].trim())) {
            for(int i = 0; i < values.length; ++i) {
                if (values[i] == null) {
                    throw new ParameterException(names[i] + " Can't be empty");
                }

                if (values[i] instanceof String && !this.isNotBlank((String)values[i])) {
                    throw new ParameterException(names[i] + " Can't be empty");
                }

                if (values[i] instanceof Collection) {
                    Collection<?> val = (Collection)values[i];
                    Iterator var6 = val.iterator();

                    while(var6.hasNext()) {
                        Object obj = var6.next();
                        if (obj == null) {
                            throw new ParameterException(names[i] + " Elements cannot be empty");
                        }

                        if (obj instanceof String && !this.isNotBlank((String)obj)) {
                            throw new ParameterException(names[i] + " Elements cannot be empty");
                        }
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查数值大小
     * @param limit
     * @param data
     */
    private void checkNumberLimit(SizeLimit limit, JSONObject data) {
        Object value = this.valueOfPattern(limit.name, data);
        if (value == null) {
            throw new ParameterException(limit.name + " Can't be empty");
        } else {
            if (value instanceof Integer) {
                Integer val = (Integer)value;
                Integer low = Integer.valueOf(limit.low);
                Integer high = Integer.valueOf(limit.high);
                if (low > val || high < val) {
                    throw new ParameterException(String.format("%s The value crosses the boundary and %s is not in [%s,%s]", limit.name, val, limit.low, limit.high));
                }
            } else if (value instanceof BigDecimal) {
                BigDecimal val = (BigDecimal)value;
                BigDecimal low = new BigDecimal(limit.low);
                BigDecimal high = new BigDecimal(limit.high);
                if (low.compareTo(val) > 0 || high.compareTo(val) < 0) {
                    throw new ParameterException(String.format("%s The value crosses the boundary and %s is not in [%s,%s]", limit.name, val, limit.low, limit.high));
                }
            }else if (value instanceof Collection) {
                Collection val = (Collection) value;
                Object[] os= val.toArray();
                if (os.length!=0){
                    if (os[0] instanceof Integer){
                        Integer low = Integer.valueOf(limit.low);
                        Integer high = Integer.valueOf(limit.high);
                        for (Object o : os) {
                            Integer num=(Integer) o;
                            if (low > num || high < num) {
                                throw new ParameterException(String.format("%s The value crosses the boundary and %s is not in [%s,%s]", limit.name, val, limit.low, limit.high));
                            }
                        }
                    }else if (os[0] instanceof BigDecimal){
                        BigDecimal low = new BigDecimal(limit.low);
                        BigDecimal high = new BigDecimal(limit.high);
                        for (Object o : os){
                            BigDecimal num=(BigDecimal) o;
                            if (low.compareTo(num) > 0 || high.compareTo(num) < 0) {
                                throw new ParameterException(String.format("%s The value crosses the boundary and %s is not in [%s,%s]", limit.name, val, limit.low, limit.high));
                            }
                        }
                    }
                }
            }

        }
    }

    /**
     * 检查参数大小是否合规
     * 若为字符串，则对字符串的长度进行验证
     * 若为集合，则对集合内部对象的数量进行验证
     * @param limit
     * @param data
     */
    private void checkSize(SizeLimit limit, JSONObject data) {
        Object value = this.valueOfPattern(limit.name, data);
        if (value == null) {
            throw new ParameterException(limit.name + " Can't be empty");
        } else {
            if (value instanceof String) {
                String val = (String)value;
                if (val.length() < Integer.valueOf(limit.low) || val.length() > Integer.valueOf(limit.high)) {
                    throw new ParameterException(String.format("%s crosses the boundary: %s is not in [%s,%s]", limit.name, val.length(), limit.low, limit.high));
                }
            } else if (value instanceof Collection) {
                Collection val = (Collection)value;
                if (val.size() < Integer.valueOf(limit.low) || val.size() > Integer.valueOf(limit.high)) {
                    throw new ParameterException(String.format("%s crosses the boundary: %s is not in [%s,%s]", limit.name, val.size(), limit.low, limit.high));
                }
            }

        }
    }

    /**
     * 将object.param1形式的参数对应的值取出
     * 若存在object.param1：直接返回
     * 若不存在object.param1
     * *******先验证是否有object
     * *******然后找到param1
     * *******在data中增加对应的记录，下次调用可加速
     * @param pattern
     * @param data
     * @return
     */
    private Object valueOfPattern(String pattern, JSONObject data) {
        if (data.containsKey(pattern)) {
            return data.get(pattern);
        } else {
            String[] params = pattern.split("\\.");
            Object value = null;
            String[] var6 = params;
            int var7 = params.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                String param = var6[var8];
                value = data.get(param);
                //若不存在该对象，则不会满足有对象参数的情况，直接返回，不再继续循环
                if (value == null) {
                    return null;
                }

                if (value instanceof JSONObject) {
                    data = (JSONObject)value;
                }
            }
            //若此处未找到，也存储null进入
            data.put(pattern, value);
            return value;
        }
    }

    /**
     * 将参数字符串中所有的空格删除
     * 优化：使用了replaceAll（正则表达式）进行替换
     * @param str
     * @return
     */
    private String transform(String str) {
        str=str.replaceAll(" ","");
        return str;
    }

    /**
     * 该方法对一对参数进行校验
     * @throws ParameterException 参数不合格异常
     * @param pattern 被验证参数名
     * @param data 被验证数据
     * @param strictCollection 若为true则为开启严格模式，集合内部的所有元素也不能为空
     */
    private void checkNull(String pattern,JSONObject data,boolean strictCollection) {
        Object value = this.valueOfPattern(pattern, data);
        if (value == null) {
            throw new ParameterException(pattern + " Can't be empty");
        } else if (value instanceof String) {
            if (!isNotBlank((String) value)) {
                throw new ParameterException(pattern + " Can't be empty");
            }
        } else {
            //开启严格集合模式，集合内部元素不能为空,默认开启
            if (value instanceof Collection && strictCollection) {
                Collection<?> val = (Collection)value;
                Iterator var5 = val.iterator();

                while(var5.hasNext()) {
                    Object obj = var5.next();
                    if (obj == null) {
                        throw new ParameterException(pattern + " Elements cannot be empty");
                    }

                    if (obj instanceof String && !this.isNotBlank((String)obj)) {
                        throw new ParameterException(pattern + " Elements cannot be empty");
                    }
                }
            }

        }
    }

    /**
     * 确认字符串是否为空
     * 若为字符串，则字符串将被去掉前后空符号后，验证是否为空
     * @param s
     * @return
     */
    private boolean isNotBlank(String s) {
        return s != null && s.trim().length() != 0;
    }

    /**
     * 解析参数，将字符串转化为Java内部类表示的信息
     * 参数格式：name[start,end]
     * 解析步骤：
     * *********去除空格
     * *********定位[,]后进行分割
     * @param pattern
     * @return
     */
    private SizeLimit resolvePattern(String pattern) {
        SizeLimit res = new SizeLimit();
        int middle = pattern.indexOf("[");
        if (middle == -1) {
            throw new ParameterException(pattern + " Misconfiguration");
        } else {
            res.name = pattern.substring(0, middle);
            int index = pattern.indexOf(",");
            if (index == -1) {
                throw new ParameterException(pattern + " Misconfiguration");
            } else {
                res.low = pattern.substring(middle + 1, index);
                res.high = pattern.substring(index + 1, pattern.length() - 1);
                return res;
            }
        }
    }
    static {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.info("SWUST OJ Verify DebugModeEnabled");
        }

    }

    private static class SizeLimit {
        String name;
        String low;
        String high;
        boolean reverse;

        private SizeLimit() {
        }
    }
}
