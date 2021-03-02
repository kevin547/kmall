package com.jjsj.mall.front.controller;

import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.marketing.response.StoreCouponUserResponse;
import com.jjsj.mall.marketing.service.StoreCouponUserService;
import com.jjsj.mall.front.request.UserCouponReceiveRequest;
import com.jjsj.mall.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 优惠卷控制器

 */
@Slf4j
@RestController
@RequestMapping("api/front/coupon")
@Api(tags = "营销 -- 优惠券")
public class UserCouponController {

    @Autowired
    private StoreCouponUserService storeCouponUserService;

    @Autowired
    private UserService userService;

    /**
     * 我的优惠券
     *  @author kepler
     * @since 2020-05-18
     */
    @ApiOperation(value = "我的优惠券")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<List<StoreCouponUserResponse>>  getList(){
        return CommonResult.success(storeCouponUserService.getListFront(userService.getUserIdException(), new PageParamRequest()));
    }

    /**
     * 领券
     * @param request UserCouponReceiveRequest 新增参数
     */
    @ApiOperation(value = "领券")
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public CommonResult<String> receive(@RequestBody @Validated UserCouponReceiveRequest request){
        if(storeCouponUserService.receiveCoupon(request)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

}



