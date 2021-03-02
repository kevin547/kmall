package com.jjsj.mall.system.controller;

import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.system.response.SystemWriteOffOrderResponse;;
import com.jjsj.mall.store.service.StoreOrderService;
import com.jjsj.mall.system.request.SystemWriteOffOrderSearchRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 订单表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/system/store/order")
@Api(tags = "设置 -- 提货点 -- 核销订单") //配合swagger使用
public class SystemWriteOffOrderController {

    @Autowired
    private StoreOrderService storeOrderService;

    /**
     * 分页显示订单表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     *  @author kepler
     * @since 2020-05-28
     */
    @ApiOperation(value = "分页列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonResult<SystemWriteOffOrderResponse>  getList(
            @Validated SystemWriteOffOrderSearchRequest request,
            @Validated PageParamRequest pageParamRequest){
        return CommonResult.success(storeOrderService.getWriteOffList(request, pageParamRequest));
    }
}



