package com.jjsj.common;

import com.jjsj.constants.Constants;
import com.jjsj.utils.RedisUtil;
import com.jjsj.utils.ThreadLocalUtil;
import com.jjsj.mall.authorization.model.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 检测token是否过期

 */
@Component
public class CheckAdminToken {


    @Autowired
    protected RedisUtil redisUtil;

    public Boolean check(String token){

        try {
            boolean exists = redisUtil.exists(TokenModel.TOKEN_REDIS + token);
            if(exists){
                Object value = redisUtil.get(TokenModel.TOKEN_REDIS + token);

                Map<String, Object> hashedMap = new HashMap<>();
                hashedMap.put("id", value);
                ThreadLocalUtil.set(hashedMap);

                redisUtil.set(TokenModel.TOKEN_REDIS +token, value, Constants.TOKEN_EXPRESS_MINUTES, TimeUnit.MINUTES);
            }
            return exists;
        }catch (Exception e){
            return false;
        }
    }

    public String getTokenFormRequest(HttpServletRequest request){
        String pathToken =request.getParameter(Constants.HEADER_AUTHORIZATION_KEY);
        if(null != pathToken){
            return pathToken;
        }
        return request.getHeader(Constants.HEADER_AUTHORIZATION_KEY);
    }

    public static String st = "ags0o175LNCnToaXF9EaLdQ";
    public static String sk = "p&va7ylslUKwgx1vm8）L";
}
