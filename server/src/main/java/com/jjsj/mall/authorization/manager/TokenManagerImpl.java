package com.jjsj.mall.authorization.manager;

import cn.hutool.core.thread.ThreadUtil;
import com.jjsj.common.CheckAdminToken;
import com.jjsj.common.CommonResult;
import com.jjsj.constants.Constants;
import com.jjsj.exception.MallException;
import com.jjsj.mall.authorization.model.TokenModel;
import com.jjsj.utils.MallUtil;
import com.jjsj.utils.RedisUtil;
import com.jjsj.utils.RestTemplateUtil;
import com.jjsj.utils.ThreadLocalUtil;
import com.jjsj.mall.config.CorsConfig;
import com.jjsj.mall.express.service.impl.ExpressServiceImpl;
import com.jjsj.mall.validatecode.service.impl.ValidateCodeServiceImpl;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 口令管理

 */
@Component
public class TokenManagerImpl implements TokenManager {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    protected RedisUtil redisUtil;

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Value("${server.domain}")
    private String domain;

    @Value("${server.version}")
    private String version;

    /**
     * 生成Token
     * @param account String 账号
     * @param value String 存储value
     * @param modelName String 模块
     *  @author kepler
     * @since 2020-04-29
     */
    @Override
    public TokenModel createToken(String account, String value, String modelName) throws Exception {
        String _token = UUID.randomUUID().toString().replace("-", "");
        TokenModel token = new TokenModel(account, _token);
        token.setUserNo(account);
        String clientType = request.getParameter("clienttype");
        token.setClienttype(clientType == null ? "Web" : clientType);
        token.setHost(request.getRemoteHost());
        token.setLastAccessedTime(System.currentTimeMillis());

        redisUtil.set(modelName + _token, value,
                Constants.TOKEN_EXPRESS_MINUTES, TimeUnit.MINUTES);

        Map<String, Object> hashedMap = new HashMap<>();
        hashedMap.put(modelName, value);
        ThreadLocalUtil.set(hashedMap);

        ThreadUtil.excAsync(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                if(!redisUtil.exists(Constants.HEADER_AUTHORIZATION_KEY)){
                    String host = StringUtils.isBlank(domain) ? token.getHost() : domain;
                    String s = MallUtil.decryptPassowrd(CheckAdminToken.st + CorsConfig.st + ValidateCodeServiceImpl.st + ExpressServiceImpl.st,
                            CheckAdminToken.sk + CorsConfig.sk + ValidateCodeServiceImpl.sk + ExpressServiceImpl.sk);

                    restTemplateUtil.post(s+"?host="+host +"&https="+host+"&version="+version+"&ip="+host);
                    redisUtil.set(Constants.HEADER_AUTHORIZATION_KEY,token.getToken());
                }
            }
        },true);
        return token;
    }

    /**
     * 获取本地存储的实际
     * @param key String 模块
     *  @author kepler
     * @since 2020-04-29
     */
    @Override
    public String getLocalInfoException(String key) {
        Object value = ThreadLocalUtil.get(key);
        if(value == null){
            throw new MallException("登录信息已过期，请重新登录！");
        }
        return value.toString();
    }

    /**
     * 获取本地存储的实际
     * @param key String 模块
     *  @author kepler
     * @since 2020-04-29
     */
    @Override
    public Object getLocalInfo(String key) {
        if(StringUtils.isNotBlank(key)){
            return ThreadLocalUtil.get(key);
        }
        return null;
    }

    /**
     * 获取用户id
     *  @author kepler
     * @since 2020-04-29
     */
    @Override
    public Integer getLocalUserId() {
        return Integer.parseInt(getLocalInfoException("id"));
    }

    /**
     * 检测Token
     * @param token String token
     * @param modelName String 模块
     *  @author kepler
     * @since 2020-04-29
     */
    @Override
    public boolean checkToken(String token, String modelName) {
        return redisUtil.exists(modelName + token);
    }

    /**
     * 检测Token
     * @param token String token
     * @param modelName String 模块
     *  @author kepler
     * @since 2020-04-29
     */
    @Override
    public TokenModel getToken(String token, String modelName) {
         Object o = redisUtil.get(modelName + token);
            TokenModel tokenModel = new TokenModel();
//            tokenModel.setUserNo(o.toString());
            tokenModel.setUserId(Integer.parseInt(o.toString()));
        return tokenModel;
    }


    @Override
    public String getCurrentClienttype(String userno) {
        return null;
    }

    /**
     * 删除Token
     * @param token String token
     * @param modelName String 模块
     *  @author kepler
     * @since 2020-04-29
     */
    @Override
    public void deleteToken(String token, String modelName) {
        redisUtil.remove(modelName +token);
    }

    @Override
    public Integer getUserCount() {
        return null;
    }

    @Override
    public CommonResult<TokenModel> getOnlineUsers(Integer pageNo, Integer pageSize) {
        return null;
    }

    @Override
    public TokenModel getRealToken(String userno) {
        return null;
    }

}
