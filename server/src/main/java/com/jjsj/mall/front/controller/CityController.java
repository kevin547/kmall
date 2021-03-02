package com.jjsj.mall.front.controller;


import com.jjsj.common.CommonResult;
import com.jjsj.mall.system.service.SystemCityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 城市服务
 
 */
@Slf4j
@RestController("CityFrontController")
@RequestMapping("api/front/city")
@Api(tags = "城市服务")
public class CityController {
    @Autowired
    private SystemCityService systemCityService;

    /**
     * 城市服务树形结构数据
     *  @author kepler
     * @since 2020-06-01
     */
    @ApiOperation(value = "树形结构")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<Object> register(){
        return CommonResult.success(systemCityService.getListTree());
    }
}



