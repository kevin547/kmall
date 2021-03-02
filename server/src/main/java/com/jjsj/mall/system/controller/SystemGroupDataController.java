package com.jjsj.mall.system.controller;

import com.jjsj.common.CommonPage;
import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.system.request.SystemGroupDataRequest;
import com.jjsj.mall.system.request.SystemGroupDataSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import com.jjsj.mall.system.service.SystemGroupDataService;
import com.jjsj.mall.system.model.SystemGroupData;


/**
 * 组合数据详情表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/system/group/data")
@Api(tags = "设置 -- 组合数据 -- 详情")
public class SystemGroupDataController {

    @Autowired
    private SystemGroupDataService systemGroupDataService;

    /**
     * 分页显示组合数据详情表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     *  @author kepler
     * @since 2020-05-15
     */
    @ApiOperation(value = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<SystemGroupData>>  getList(@Validated SystemGroupDataSearchRequest request, @Validated PageParamRequest pageParamRequest){
        CommonPage<SystemGroupData> systemGroupDataCommonPage = CommonPage.restPage(systemGroupDataService.getList(request, pageParamRequest));
        return CommonResult.success(systemGroupDataCommonPage);
    }

    /**
     * 新增组合数据详情表
     * @param systemGroupDataRequest SystemFormCheckRequest 新增参数
     *  @author kepler
     * @since 2020-05-15
     */
    @ApiOperation(value = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated SystemGroupDataRequest systemGroupDataRequest){
        if(systemGroupDataService.create(systemGroupDataRequest)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 删除组合数据详情表
     * @param id Integer
     *  @author kepler
     * @since 2020-05-15
     */
    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id){
        if(systemGroupDataService.removeById(id)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 修改组合数据详情表
     * @param id integer id
     * @param request 修改参数
     *  @author kepler
     * @since 2020-05-15
     */
    @ApiOperation(value = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id, @RequestBody @Validated SystemGroupDataRequest request){
        if(systemGroupDataService.update(id, request)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 查询组合数据详情表信息
     * @param id Integer
     *  @author kepler
     * @since 2020-05-15
     */
    @ApiOperation(value = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<SystemGroupData> info(@RequestParam(value = "id") Integer id){
        SystemGroupData systemGroupData = systemGroupDataService.getById(id);
        return CommonResult.success(systemGroupData);
    }
}



