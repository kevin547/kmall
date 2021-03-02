package com.jjsj.mall.front.controller;


import com.jjsj.common.CheckAdminToken;
import com.jjsj.common.CommonResult;
import com.jjsj.utils.MallUtil;
import com.jjsj.utils.ValidateFormUtil;
import com.jjsj.mall.front.request.LoginMobileRequest;
import com.jjsj.mall.front.request.LoginRequest;
import com.jjsj.mall.front.response.LoginResponse;
import com.jjsj.mall.front.service.LoginService;
import com.jjsj.mall.sms.service.SmsService;
import com.jjsj.mall.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登陆 前端控制器
 
 */
@Slf4j
@RestController("FrontLoginController")
@RequestMapping("api/front")
@Api(tags = "用户 -- 登录注册")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    CheckAdminToken checkAdminToken;

    @Autowired
    private SmsService smsService;

    @Autowired
    private LoginService loginService;


    /**
     * 手机号登录接口
     *  @author kepler
     * @since 2020-04-28
     */
    @ApiOperation(value = "手机号登录接口")
    @RequestMapping(value = "/login/mobile", method = RequestMethod.POST)
    public CommonResult<LoginResponse> phoneLogin(@RequestBody @Validated LoginMobileRequest loginRequest, HttpServletRequest request) throws Exception {
        String clientIp = MallUtil.getClientIp(request);
        return CommonResult.success(loginService.phoneLogin(loginRequest, clientIp));
    }

    /**
     * 账号密码登录
     *  @author kepler
     * @since 2020-04-28
     */
    @ApiOperation(value = "账号密码登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult<LoginResponse> login(@RequestBody @Validated LoginRequest loginRequest) throws Exception {
        return CommonResult.success(loginService.login(loginRequest));
    }


    /**
     * 退出登录
     *  @author kepler
     * @since 2020-04-28
     */
    @ApiOperation(value = "退出")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public CommonResult<String> loginOut(HttpServletRequest request){
        userService.loginOut(checkAdminToken.getTokenFormRequest(request));
        return CommonResult.success();
    }

    /**
     * 发送短信登录验证码
     * @param phone 手机号码
     * @return 发送是否成功
     */
    @ApiOperation(value = "发送短信登录验证码")
    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name="phone", value="手机号码", required = true)
    })
    public CommonResult<Object> sendCode(@RequestParam String phone){
        ValidateFormUtil.isPhone(phone,"手机号码错误");
        if(smsService.sendCommonCode(phone)){
            return CommonResult.success("发送成功");
        }else{
            return CommonResult.failed("发送失败");
        }
    }
}



