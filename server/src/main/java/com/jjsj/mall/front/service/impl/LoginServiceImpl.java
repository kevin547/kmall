package com.jjsj.mall.front.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.jjsj.constants.Constants;
import com.jjsj.constants.SmsConstants;
import com.jjsj.exception.MallException;
import com.jjsj.mall.front.response.LoginResponse;
import com.jjsj.utils.MallUtil;
import com.jjsj.utils.DateUtil;
import com.jjsj.utils.RedisUtil;
import com.jjsj.mall.front.request.LoginMobileRequest;
import com.jjsj.mall.front.request.LoginRequest;
import com.jjsj.mall.front.service.LoginService;
import com.jjsj.mall.user.model.User;
import com.jjsj.mall.user.service.UserService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 移动端登录服务类 
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 账号密码登录
     */
    @Override
    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        User user = userService.getUserByAccount(loginRequest.getPhone());
        if (ObjectUtil.isNull(user)) {
            throw new MallException("此账号未注册");
        }
        if (!user.getStatus()) {
            throw new MallException("此账号被禁用");
        }

        // 校验密码
        String password = MallUtil
            .encryptPassword(loginRequest.getPassword(), loginRequest.getPhone());
        if (!user.getPwd().equals(password)) {
            throw new MallException("密码错误");
        }

        LoginResponse loginResponse = new LoginResponse();
        String token = userService.token(user);
        loginResponse.setToken(token);
        user.setPwd(null);

        //绑定推广关系
        if (user.getSpreadUid() < 1 && loginRequest.getSpreadPid() > 0) {
            bindSpread(user, loginRequest.getSpreadPid());
        }

        // 记录最后一次登录时间
        user.setLastLoginTime(DateUtil.nowDateTime());
        userService.updateById(user);

        loginResponse.setUser(user);
        long time = Constants.TOKEN_EXPRESS_MINUTES * 60;
        loginResponse.setExpiresTime(DateUtil.addSecond(DateUtil.nowDateTime(), (int) time));
        return loginResponse;
    }

    /**
     * 手机号验证码登录
     *
     * @param loginRequest 登录请求信息
     * @param clientIp ip
     * @return LoginResponse
     */
    @Override
    public LoginResponse phoneLogin(LoginMobileRequest loginRequest, String clientIp)
        throws Exception {
        //检测验证码
        checkValidateCode(loginRequest.getPhone(), loginRequest.getValidateCode());

        //查询手机号信息
        User user = userService.getUserByAccount(loginRequest.getPhone());
        Integer spread = Optional.ofNullable(loginRequest.getSpread()).orElse(0);
        if (ObjectUtil.isNull(user)) {
            //此用户不存在，走新用户注册流程
            user = userService.registerPhone(loginRequest.getPhone(), spread, clientIp);
        } else {
            // 正常流程
            // 绑定推广关系
            if (user.getSpreadUid() < 1 && spread > 0) {
                bindSpread(user, spread);
            }
        }

        // 记录最后一次登录时间
        user.setLastLoginTime(DateUtil.nowDateTime());
        userService.updateById(user);

        //生成token
        LoginResponse loginResponse = new LoginResponse();
        String token = userService.token(user);
        loginResponse.setToken(token);
        user.setPwd(null);

        loginResponse.setUser(user);
        long time = Constants.TOKEN_EXPRESS_MINUTES * 60;
        loginResponse.setExpiresTime(DateUtil.addSecond(DateUtil.nowDateTime(), (int) time));
        return loginResponse;
    }

    /**
     * 检测手机验证码
     *
     * @param phone 手机号
     * @param code 验证码
     */
    private void checkValidateCode(String phone, String code) {
        Object validateCode = redisUtil.get(SmsConstants.SMS_VALIDATE_PHONE + phone);
        if (validateCode == null) {
            throw new MallException("验证码已过期");
        }

        if (!validateCode.toString().equals(code)) {
            throw new MallException("验证码错误");
        }

        //删除验证码
        redisUtil.remove(SmsConstants.SMS_VALIDATE_PHONE + phone);
    }

    /**
     * 绑定分销关系
     *
     * @param user User 用户user类
     * @param spreadUid Integer 推广人id
     * @return Boolean 1.判断分销功能是否启用 2.判断分销模式 3.根据不同的分销模式校验 4.指定分销，只有分销员才可以分销，需要spreadUid是推广员才可以绑定
     * 5.满额分销，同上 6.人人分销，可以直接绑定
     */
    private Boolean bindSpread(User user, Integer spreadUid) {
        Boolean checkBingSpread = userService.checkBingSpread(user, spreadUid, "old");
        if (!checkBingSpread) {
            return false;
        }

        user.setSpreadUid(spreadUid);
        user.setSpreadTime(DateUtil.nowDateTime());

        Boolean execute = transactionTemplate.execute(e -> {
            userService.updateById(user);
            userService.updateSpreadCountByUid(spreadUid);
            return Boolean.TRUE;
        });
        if (!execute) {
            logger.error(
                StrUtil.format("绑定推广人时出错，userUid = {}, spreadUid = {}", user.getUid(), spreadUid));
        }
        return execute;
    }
}
