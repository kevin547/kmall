package com.jjsj.mall.front.service;

import com.jjsj.mall.front.response.LoginResponse;
import com.jjsj.mall.front.request.LoginMobileRequest;
import com.jjsj.mall.front.request.LoginRequest;

/**
 * 移动端登录服务类 
 */
public interface LoginService {

    /**
     * 账号密码登录
     *
     * @return LoginResponse
     */
    LoginResponse login(LoginRequest loginRequest) throws Exception;

    /**
     * 手机号验证码登录
     */
    LoginResponse phoneLogin(LoginMobileRequest loginRequest, String clientIp) throws Exception;
}
