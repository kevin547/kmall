package com.jjsj.mall.user.controller;

import com.jjsj.common.CommonPage;
import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.user.model.UserTag;
import com.jjsj.mall.user.request.UserTagRequest;
import com.jjsj.mall.user.service.UserService;
import com.jjsj.mall.user.service.UserTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户标签 前端控制器 
 */
@Slf4j
@RestController
@RequestMapping("api/admin/user/tag")
@Api(tags = "会员 -- 标签") //配合swagger使用

public class UserTagController {

    @Autowired
    private UserTagService userTagService;

    @Autowired
    private UserService userService;

    /**
     * 分页显示用户分标签
     *
     * @param pageParamRequest 分页参数
     */
    @ApiOperation(value = "分页列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserTag>> getList(@Validated PageParamRequest pageParamRequest) {
        CommonPage<UserTag> userTagCommonPage = CommonPage
            .restPage(userTagService.getList(pageParamRequest));
        return CommonResult.success(userTagCommonPage);
    }

    /**
     * 新增用户分标签
     *
     * @param userTagRequest 新增参数
     */
    @ApiOperation(value = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated UserTagRequest userTagRequest) {
        UserTag userTag = new UserTag();
        BeanUtils.copyProperties(userTagRequest, userTag);

        if (userTagService.save(userTag)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 删除用户分标签
     *
     * @param id Integer
     */
    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id) {
        if (userTagService.removeById(id)) {
            userService.clearGroupByGroupId(id + "");
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 修改用户标签
     *
     * @param id integer id
     * @param userTagRequest 修改参数
     */
    @ApiOperation(value = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id,
        @RequestBody @Validated UserTagRequest userTagRequest) {
        UserTag userTag = new UserTag();
        BeanUtils.copyProperties(userTagRequest, userTag);
        userTag.setId(id);

        if (userTagService.updateById(userTag)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 查询用户标签
     *
     * @param id Integer
     */
    @ApiOperation(value = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<UserTag> info(@RequestParam(value = "id") Integer id) {
        UserTag userTag = userTagService.getById(id);
        return CommonResult.success(userTag);
    }
}



