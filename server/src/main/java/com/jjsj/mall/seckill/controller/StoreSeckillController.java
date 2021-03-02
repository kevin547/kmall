package com.jjsj.mall.seckill.controller;

import com.jjsj.common.CommonPage;
import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.seckill.request.StoreSeckillRequest;
import com.jjsj.mall.seckill.request.StoreSeckillSearchRequest;
import com.jjsj.mall.seckill.response.StoreSeckillResponse;
import com.jjsj.mall.seckill.service.StoreSeckillService;
import com.jjsj.mall.store.response.StoreProductResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 商品秒杀产品表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/store/seckill")
@Api(tags = "商品 -- 秒杀 -- 商品") //配合swagger使用

public class StoreSeckillController {

    @Autowired
    private StoreSeckillService storeSeckillService;

    /**
     * 分页显示商品秒杀产品表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     * @author keplerStivepeim
     * @since 2020-09-17
     */
    @ApiOperation(value = "分页列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreSeckillResponse>>  getList(@Validated StoreSeckillSearchRequest request, @Validated PageParamRequest pageParamRequest){
        CommonPage<StoreSeckillResponse> storeSeckillCommonPage =
                CommonPage.restPage(storeSeckillService.getList(request, pageParamRequest));
        return CommonResult.success(storeSeckillCommonPage);
    }

    /**
     * 新增商品秒杀产品表
     * @param storeSeckillRequest 新增参数
     * @author keplerStivepeim
     * @since 2020-09-17
     */
    @ApiOperation(value = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated StoreSeckillRequest storeSeckillRequest){
        if(storeSeckillService.saveSeckill(storeSeckillRequest)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 删除商品秒杀产品表
     * @param id Integer
     * @author keplerStivepeim
     * @since 2020-09-17
     */
    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id){
        if(storeSeckillService.deleteById(id)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 修改商品秒杀产品表
     * @param id integer id
     * @param storeSeckillRequest 修改参数
     * @author keplerStivepeim
     * @since 2020-09-17
     */
    @ApiOperation(value = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id, @RequestBody @Validated StoreSeckillRequest storeSeckillRequest){
        storeSeckillRequest.setId(id);
        if(storeSeckillService.updateSeckill(storeSeckillRequest)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "修改秒杀商品状态")
    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    public CommonResult<String> updateStatus(@RequestParam @Validated Integer id, @RequestParam @Validated boolean status){
//        if(null == id || null ==status || status < 0 || status >= 2) throw new MallException("参数不合法");
        if(storeSeckillService.updateSecKillStatus(id,status)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 查询商品秒杀产品表信息
     * @param id Integer
     * @author keplerStivepeim
     * @since 2020-09-17
     */
    @ApiOperation(value = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<StoreProductResponse> info(@RequestParam(value = "id") Integer id){
        StoreProductResponse storeSeckill = storeSeckillService.getDetailAdmin(id);
        return CommonResult.success(storeSeckill);
   }
}



