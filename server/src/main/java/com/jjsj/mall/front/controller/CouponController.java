package com.jjsj.mall.front.controller;

import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.marketing.response.StoreCouponFrontResponse;
import com.jjsj.mall.marketing.response.StoreCouponUserOrder;
import com.jjsj.mall.marketing.service.StoreCouponService;
import com.jjsj.mall.marketing.service.StoreCouponUserService;
import com.jjsj.utils.MallUtil;
import com.jjsj.mall.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * 优惠券表 前端控制器
 
 */
@Slf4j
@RestController("CouponFrontController")
@RequestMapping("api/front")
@Api(tags = "优惠券")

public class CouponController {

    @Autowired
    private StoreCouponService storeCouponService;

    @Autowired
    private StoreCouponUserService storeCouponUserService;

    @Autowired
    private UserService userService;


    /**
     * 分页显示优惠券表
     * @param type 类型，搜索产品指定优惠券
     * @param productId 产品id，搜索产品指定优惠券
     * @param pageParamRequest 分页参数
     *  @author kepler
     * @since 2020-05-18
     */
    @ApiOperation(value = "分页列表")
    @RequestMapping(value = "/coupons", method = RequestMethod.GET)
    public CommonResult<List<StoreCouponFrontResponse>>  getList(
            @RequestParam(value = "type", defaultValue = "0") int type,
            @RequestParam(value = "productId", defaultValue = "0") int productId,
            @Validated PageParamRequest pageParamRequest){

        if(type == 0){
            productId = 0;
        }

        return CommonResult.success(storeCouponService.getListByUser(productId, pageParamRequest, userService.getUserId()));
    }

    /**
     * 根据购物车id获取可用优惠券
     * @param cartId 购物车id
     * @return 优惠券集合
     */
    @ApiOperation(value = "当前购物车可用优惠券")
    @RequestMapping(value = "coupons/order", method = RequestMethod.GET)
    public CommonResult<List<StoreCouponUserOrder>> getCouponsListByCartId(@RequestParam String cartId){
        try {
            MallUtil.stringToArrayInt(cartId);
        } catch (NumberFormatException e) {
            return CommonResult.success(new ArrayList<StoreCouponUserOrder>());
        }
        return CommonResult.success(storeCouponUserService.getListByCartIds(MallUtil.stringToArrayInt(cartId)));
    }
}



