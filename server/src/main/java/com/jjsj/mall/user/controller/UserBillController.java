package com.jjsj.mall.user.controller;

import com.jjsj.common.CommonPage;
import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.finance.request.FundsMonitorSearchRequest;
import com.jjsj.mall.user.model.UserBill;
import com.jjsj.mall.user.request.UserBillRequest;
import com.jjsj.mall.user.response.UserBillResponse;
import com.jjsj.mall.user.service.UserBillService;
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
 * 用户账单表 前端控制器 
 */
@Slf4j
@RestController
@RequestMapping("api/admin/user/bill")
@Api(tags = "会员管理 -- 用户账单")
public class UserBillController {

    @Autowired
    private UserBillService userBillService;

    /**
     * 分页显示用户账单表
     *
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     *  @author kepler
     * @since 2020-04-28
     */
    @ApiOperation(value = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonResult<CommonPage<UserBillResponse>> getList(
        @RequestBody @Validated FundsMonitorSearchRequest request,
        @Validated PageParamRequest pageParamRequest) {
        CommonPage<UserBillResponse> userBillCommonPage = CommonPage
            .restPage(userBillService.getListAdmin(request, pageParamRequest));
        return CommonResult.success(userBillCommonPage);
    }

    /**
     * 新增用户账单表
     *
     * @param userBillRequest 新增参数
     *  @author kepler
     * @since 2020-04-28
     */
    @ApiOperation(value = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@Validated UserBillRequest userBillRequest) {
        UserBill userBill = new UserBill();
        BeanUtils.copyProperties(userBillRequest, userBill);

        if (userBillService.save(userBill)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 删除用户账单表
     *
     * @param id Integer
     *  @author kepler
     * @since 2020-04-28
     */
    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id) {
        if (userBillService.removeById(id)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 修改用户账单表
     *
     * @param id integer id
     * @param userBillRequest 修改参数
     *  @author kepler
     * @since 2020-04-28
     */
    @ApiOperation(value = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id,
        @Validated UserBillRequest userBillRequest) {
        UserBill userBill = new UserBill();
        BeanUtils.copyProperties(userBillRequest, userBill);
        userBill.setId(id);

        if (userBillService.updateById(userBill)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 查询用户账单表信息
     *
     * @param id Integer
     *  @author kepler
     * @since 2020-04-28
     */
    @ApiOperation(value = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<UserBill> info(@RequestParam(value = "id") Integer id) {
        return CommonResult.success(userBillService.getById(id));
    }
}



