package com.jjsj.mall.user.controller;


import com.jjsj.common.CommonPage;
import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.user.model.User;
import com.jjsj.mall.user.request.UserOperateIntegralMoneyRequest;
import com.jjsj.mall.user.request.UserRequest;
import com.jjsj.mall.user.request.UserSearchRequest;
import com.jjsj.mall.user.request.UserUpdateSpreadRequest;
import com.jjsj.mall.user.response.TopDetail;
import com.jjsj.mall.user.response.UserResponse;
import com.jjsj.mall.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户表 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("api/admin/user")
@Api(tags = "会员管理")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 分页显示用户表
     *
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     *  @author kepler
     * @since 2020-04-10
     */
    @ApiOperation(value = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserResponse>> getList(@ModelAttribute UserSearchRequest request,
        @ModelAttribute PageParamRequest pageParamRequest) {
        CommonPage<UserResponse> userCommonPage = CommonPage
            .restPage(userService.getList(request, pageParamRequest));
        return CommonResult.success(userCommonPage);
    }

    /**
     * 删除用户表
     *
     * @param id Integer
     *  @author kepler
     * @since 2020-04-10
     */
    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id) {
        if (userService.removeById(id)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 修改用户表
     *
     * @param id integer id
     * @param userRequest 修改参数
     *  @author kepler
     * @since 2020-04-10
     */
    @ApiOperation(value = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id,
        @RequestBody UserRequest userRequest) {
        userRequest.setUid(id);
        if (userService.updateUser(userRequest)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 查询用户表信息
     *
     * @param id Integer
     *  @author kepler
     * @since 2020-04-10
     */
    @ApiOperation(value = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<User> info(@RequestParam(value = "id") Integer id) {
        User user = userService.getById(id);
        return CommonResult.success(user);
    }

    /**
     * 根据参数类型查询会员对应的信息
     *
     * @param userId Integer 会员id
     * @param type int 类型 0=消费记录，1=积分明细，2=签到记录，3=持有优惠券，4=余额变动，5=好友关系
     * @param pageParamRequest PageParamRequest 分页
     * @author keplerstivepeim
     * @since 2020-04-10
     */
    @ApiOperation(value = "会员详情")
    @RequestMapping(value = "/infobycondition", method = RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userId", example = "1", required = true),
        @ApiImplicitParam(name = "type", value = "0=消费记录，1=积分明细，2=签到记录，3=持有优惠券，4=余额变动，5=好友关系", example = "0"
            , required = true)
    })
    public CommonResult<CommonPage<T>> infoByCondition(
        @RequestParam(name = "userId") @Valid Integer userId,
        @RequestParam(name = "type") @Valid @Max(5) @Min(0) int type,
        @ModelAttribute PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage
            .restPage((List<T>) userService.getInfoByCondition(userId, type, pageParamRequest)));
    }

    /**
     * 获取会员详情对应数据
     */
    @ApiOperation(value = "会员详情页Top数据")
    @RequestMapping(value = "topdetail", method = RequestMethod.GET)
    public CommonResult<TopDetail> topDetail(@RequestParam @Valid Integer userId) {
        return CommonResult.success(userService.getTopDetail(userId));
    }

    /**
     * 操作积分
     */
    @ApiOperation(value = "积分余额")
    @RequestMapping(value = "/operate/founds", method = RequestMethod.GET)
    public CommonResult<Object> founds(@Validated UserOperateIntegralMoneyRequest request) {
        if (userService.updateIntegralMoney(request)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 会员分组
     *
     * @param id String id
     * @param groupId Integer 分组Id
     *  @author kepler
     * @since 2020-04-28
     */
    @ApiOperation(value = "分组")
    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public CommonResult<String> group(@RequestParam String id,
        @RequestParam String groupId) {
        if (userService.group(id, groupId)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 会员标签
     *
     * @param id String id
     * @param tagId Integer 标签id
     */
    @ApiOperation(value = "标签")
    @RequestMapping(value = "/tag", method = RequestMethod.POST)
    public CommonResult<String> tag(@RequestParam String id,
        @RequestParam String tagId) {
        if (userService.tag(id, tagId)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 修改上级推广人
     */
    @ApiOperation(value = "修改上级推广人")
    @RequestMapping(value = "/update/spread", method = RequestMethod.POST)
    public CommonResult<String> editSpread(
        @Validated @RequestBody UserUpdateSpreadRequest request) {
        if (userService.editSpread(request)) {
            return CommonResult.success("修改成功");
        } else {
            return CommonResult.failed("修改失败");
        }
    }
}



