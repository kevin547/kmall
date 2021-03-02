package com.jjsj.mall.marketing.controller;

import com.jjsj.common.CommonPage;
import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.marketing.request.StoreCouponUserRequest;
import com.jjsj.mall.marketing.request.StoreCouponUserSearchRequest;
import com.jjsj.mall.marketing.response.StoreCouponUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import com.jjsj.mall.marketing.service.StoreCouponUserService;


/**
 * 优惠券发放记录表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/marketing/coupon/user")
@Api(tags = "营销 -- 优惠券 -- 领取记录")
public class StoreCouponUserController {

    @Autowired
    private StoreCouponUserService storeCouponUserService;

    /**
     * 分页显示优惠券发放记录表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     *  @author kepler
     * @since 2020-05-18
     */
    @ApiOperation(value = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreCouponUserResponse>>  getList(@Validated StoreCouponUserSearchRequest request, @Validated PageParamRequest pageParamRequest){
        CommonPage<StoreCouponUserResponse> storeCouponUserCommonPage = CommonPage.restPage(storeCouponUserService.getList(request, pageParamRequest));
        return CommonResult.success(storeCouponUserCommonPage);
    }

    /**
     * 领券
     * @param storeCouponUserRequest 新增参数
     *  @author kepler
     * @since 2020-05-18
     */
    @ApiOperation(value = "领券")
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public CommonResult<String> receive(@Validated StoreCouponUserRequest storeCouponUserRequest){
        if(storeCouponUserService.receive(storeCouponUserRequest)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }
}



