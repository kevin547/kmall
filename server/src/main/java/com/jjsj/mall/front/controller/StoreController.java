package com.jjsj.mall.front.controller;


import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.system.service.SystemStoreService;
import com.jjsj.mall.front.request.StoreNearRequest;
import com.jjsj.mall.front.response.StoreNearResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提货点
 
 */
@Slf4j
@RestController("StoreController")
@RequestMapping("api/front/store")
@Api(tags = "提货点")
public class StoreController {
    @Autowired
    private SystemStoreService systemStoreService;

    /**
     * 附近的提货点
     *  @author kepler
     * @since 2020-06-01
     */
    @ApiOperation(value = "附近的提货点")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonResult<StoreNearResponse> register(@Validated StoreNearRequest request, @Validated PageParamRequest pageParamRequest){
        return CommonResult.success(systemStoreService.getNearList(request, pageParamRequest));
    }
}



