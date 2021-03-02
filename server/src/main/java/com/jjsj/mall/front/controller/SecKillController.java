package com.jjsj.mall.front.controller;

import com.jjsj.common.CommonPage;
import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.seckill.response.StoreSeckillDetailResponse;
import com.jjsj.mall.seckill.response.StoreSeckillResponse;
import com.jjsj.mall.seckill.service.StoreSeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * SecKillController
 
 */
@Slf4j
@RestController
@RequestMapping("api/front/seckill")
@Api(tags = "秒杀商品")
public class SecKillController {

    @Autowired
    StoreSeckillService storeSeckillService;

    /**
     * 秒杀Index
     * @return 可秒杀配置
     */
    @ApiOperation(value = "秒杀Header")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public CommonResult<HashMap<String,Object>> index(){
        return CommonResult.success(storeSeckillService.getForH5Index());
    }

    /**
     * 根据时间段查询秒杀信息
     * @return 查询时间内的秒杀商品列表
     */
    @ApiOperation(value = "秒杀列表")
    @RequestMapping(value = "/list/{timeId}", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreSeckillResponse>> list(@PathVariable("timeId") String timeId, @ModelAttribute PageParamRequest pageParamRequest){
        CommonPage<StoreSeckillResponse> secKillCommonPage =
                CommonPage.restPage(storeSeckillService.getKillListByTimeId(timeId,pageParamRequest,true));
        return CommonResult.success(secKillCommonPage);
    }


    @ApiOperation(value = "详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public CommonResult<StoreSeckillDetailResponse> info(@PathVariable(value = "id") Integer id){
        StoreSeckillDetailResponse storeSeckill = storeSeckillService.getDetailH5(id);
        return CommonResult.success(storeSeckill);
    }
}
