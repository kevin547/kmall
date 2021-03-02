package com.jjsj.mall.system.controller;

import com.jjsj.common.CommonPage;
import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.system.request.SystemGroupRequest;
import com.jjsj.mall.system.request.SystemGroupSearchRequest;
import com.jjsj.mall.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import com.jjsj.mall.system.service.SystemGroupService;
import com.jjsj.mall.system.model.SystemGroup;


/**
 * 组合数据表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/system/group")
@Api(tags = "设置 -- 组合数据")
public class SystemGroupController {

    @Autowired
    private SystemGroupService systemGroupService;

    @Autowired
    private UserService userService;

    /**
     * 分页显示组合数据表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     *  @author kepler
     * @since 2020-05-15
     */
    @ApiOperation(value = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<SystemGroup>>  getList(@Validated SystemGroupSearchRequest request, @Validated PageParamRequest pageParamRequest){
        CommonPage<SystemGroup> systemGroupCommonPage = CommonPage.restPage(systemGroupService.getList(request, pageParamRequest));
        return CommonResult.success(systemGroupCommonPage);
    }

    /**
     * 新增组合数据表
     * @param systemGroupRequest 新增参数
     *  @author kepler
     * @since 2020-05-15
     */
    @ApiOperation(value = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@Validated SystemGroupRequest systemGroupRequest){
        SystemGroup systemGroup = new SystemGroup();
        BeanUtils.copyProperties(systemGroupRequest, systemGroup);

        if(systemGroupService.save(systemGroup)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 删除组合数据表
     * @param id Integer
     */
    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id){
        if(systemGroupService.removeById(id)){
            // 删除用户对应已经存在的分组标签 虽然数据库用的是String类型但逻辑仅仅只存储一个数据，这里直接删除对应的用户分组id即可
            userService.clearGroupByGroupId(id+"");
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 修改组合数据表
     * @param id integer id
     * @param systemGroupRequest 修改参数
     *  @author kepler
     * @since 2020-05-15
     */
    @ApiOperation(value = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id, @Validated SystemGroupRequest systemGroupRequest){
        SystemGroup systemGroup = new SystemGroup();
        BeanUtils.copyProperties(systemGroupRequest, systemGroup);
        systemGroup.setId(id);

        if(systemGroupService.updateById(systemGroup)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 查询组合数据表信息
     * @param id Integer
     *  @author kepler
     * @since 2020-05-15
     */
    @ApiOperation(value = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<SystemGroup> info(@RequestParam(value = "id") Integer id){
        SystemGroup systemGroup = systemGroupService.getById(id);
        return CommonResult.success(systemGroup);
   }
}



