package com.jjsj.mall.marketing.controller;

import com.jjsj.common.CommonPage;
import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.marketing.request.StoreCouponRequest;
import com.jjsj.mall.marketing.request.StoreCouponSearchRequest;
import com.jjsj.mall.marketing.response.StoreCouponInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import com.jjsj.mall.marketing.service.StoreCouponService;
import com.jjsj.mall.marketing.model.StoreCoupon;


/**
 * 优惠券表 前端控制器
 
 */
@Slf4j
@RestController
@RequestMapping("api/admin/marketing/coupon")
@Api(tags = "营销 -- 优惠券")
public class StoreCouponController {

    @Autowired
    private StoreCouponService storeCouponService;

    /**
     * 分页显示优惠券表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     *  @author kepler
     * @since 2020-05-18
     */
    @ApiOperation(value = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreCoupon>>  getList(@Validated StoreCouponSearchRequest request, @Validated PageParamRequest pageParamRequest){
        CommonPage<StoreCoupon> storeCouponCommonPage = CommonPage.restPage(storeCouponService.getList(request, pageParamRequest));
        return CommonResult.success(storeCouponCommonPage);
    }

    /**
     * 保存优惠券表
     * @param request StoreCouponRequest 新增参数
     *  @author kepler
     * @since 2020-05-18
     */
    @ApiOperation(value = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated StoreCouponRequest request){
        if(storeCouponService.create(request)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 是否有效
     * @param id integer id
     *  @author kepler
     * @since 2020-05-18
     */
    @ApiOperation(value = "修改")
    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    public CommonResult<String> updateStatus(@RequestParam Integer id, @RequestParam Boolean status){
        StoreCoupon storeCoupon = new StoreCoupon();
        storeCoupon.setId(id);
        storeCoupon.setStatus(status);
        if(storeCouponService.updateById(storeCoupon)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 详情
     * @param id integer id
     *  @author kepler
     * @since 2020-05-18
     */
    @ApiOperation(value = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public CommonResult<StoreCouponInfoResponse> info(@RequestParam Integer id){
        return CommonResult.success(storeCouponService.info(id));
    }
}



