package com.jjsj.mall.front.controller;

import com.jjsj.common.CommonPage;
import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.system.vo.SystemGroupDataSignConfigVo;
import com.jjsj.mall.front.request.UserSignInfoRequest;
import com.jjsj.mall.front.response.UserSignInfoResponse;
import com.jjsj.mall.user.service.UserSignService;
import com.jjsj.mall.user.vo.UserSignMonthVo;
import com.jjsj.mall.user.vo.UserSignVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


/**
 * 签到记录表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/front/user/sign")
@Api(tags = "用户 -- 签到")
public class UserSignController {

    @Autowired
    private UserSignService userSignService;

    /**
     * 签到列表
     * @param pageParamRequest 分页参数
     *  @author kepler
     * @since 2020-04-30
     */
    @ApiOperation(value = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserSignVo>>  getList(@Validated PageParamRequest pageParamRequest){
        CommonPage<UserSignVo> userSignCommonPage = CommonPage.restPage(userSignService.getList(pageParamRequest));
        return CommonResult.success(userSignCommonPage);
    }

    /**
     * 签到列表，年月纬度
     * @param pageParamRequest 分页参数
     *  @author kepler
     * @since 2020-04-30
     */
    @ApiOperation(value = "分页列表")
    @RequestMapping(value = "/month", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserSignMonthVo>>  getListGroupMonth(@Validated PageParamRequest pageParamRequest){
        CommonPage<UserSignMonthVo> userSignCommonPage = CommonPage.restPage(userSignService.getListGroupMonth(pageParamRequest));
        return CommonResult.success(userSignCommonPage);
    }

    /**
     * 配置
     *  @author kepler
     * @since 2020-04-30
     */
    @ApiOperation(value = "配置")
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public CommonResult<CommonPage<SystemGroupDataSignConfigVo>> config(){
        CommonPage<SystemGroupDataSignConfigVo> systemSignConfigVoCommonPage = CommonPage.restPage(userSignService.config());
        return CommonResult.success(systemSignConfigVoCommonPage);
    }

    /**
     * 查询签到记录表信息
     *  @author kepler
     * @since 2020-04-30
     */
    @ApiOperation(value = "签到")
    @RequestMapping(value = "/integral", method = RequestMethod.GET)
    public CommonResult<SystemGroupDataSignConfigVo> info(){
        return CommonResult.success(userSignService.sign());
    }

    /**
     * 查询签到记录表信息
     *  @author kepler
     * @since 2020-04-30
     */
    @ApiOperation(value = "详情")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public CommonResult<HashMap<String, Object>> get(){
        return CommonResult.success(userSignService.get());
    }

    /**
     * 查询签到记录表信息
     *  @author kepler
     * @since 2020-04-30
     */
    @ApiOperation(value = "签到用户信息")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public CommonResult<UserSignInfoResponse> getUserInfo(@RequestBody @Validated UserSignInfoRequest request){
        return CommonResult.success(userSignService.getUserInfo(request));
    }
}



