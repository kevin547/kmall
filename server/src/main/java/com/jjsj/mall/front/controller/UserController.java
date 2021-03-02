package com.jjsj.mall.front.controller;


import com.jjsj.common.CommonPage;
import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.constants.Constants;
import com.github.pagehelper.PageInfo;
import com.jjsj.mall.finance.request.UserExtractRequest;
import com.jjsj.mall.front.response.SpreadCommissionDetailResponse;
import com.jjsj.mall.front.response.UserBalanceResponse;
import com.jjsj.mall.front.response.UserCenterResponse;
import com.jjsj.mall.front.response.UserCommissionResponse;
import com.jjsj.mall.front.response.UserExtractCashResponse;
import com.jjsj.mall.front.response.UserExtractRecordResponse;
import com.jjsj.mall.front.response.UserSpreadBannerResponse;
import com.jjsj.mall.front.response.UserSpreadOrderResponse;
import com.jjsj.mall.front.response.UserSpreadPeopleResponse;
import com.jjsj.mall.system.model.SystemUserLevel;
import com.jjsj.mall.system.service.SystemAttachmentService;
import com.jjsj.mall.system.service.SystemGroupDataService;
import com.jjsj.mall.front.request.PasswordRequest;
import com.jjsj.mall.front.request.UserBindingRequest;
import com.jjsj.mall.front.request.UserEditRequest;
import com.jjsj.mall.front.request.UserSpreadPeopleRequest;
import com.jjsj.mall.front.response.*;
import com.jjsj.mall.front.service.UserCenterService;
import com.jjsj.mall.user.model.User;
import com.jjsj.mall.user.model.UserBill;
import com.jjsj.mall.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户 -- 用户中心
 
 */
@Slf4j
@RestController("FrontUserController")
@RequestMapping("api/front")
@Api(tags = "用户 -- 用户中心")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SystemGroupDataService systemGroupDataService;

    @Autowired
    private UserCenterService userCenterService;

    @Autowired
    private SystemAttachmentService systemAttachmentService;

    /**
     * 修改密码
     *  @author kepler
     * @since 2020-04-28
     */
    @ApiOperation(value = "手机号修改密码")
    @RequestMapping(value = "/register/reset", method = RequestMethod.POST)
    public CommonResult<Boolean> password(@RequestBody @Validated PasswordRequest request){
        return CommonResult.success(userService.password(request));
    }

    /**
     * 修改个人资料
     *  @author kepler
     * @since 2020-04-28
     */
    @ApiOperation(value = "修改个人资料")
    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public CommonResult<Boolean> personInfo(@RequestBody @Validated UserEditRequest request){
        User user = userService.getInfo();
        user.setAvatar(systemAttachmentService.clearPrefix(request.getAvatar()));
        user.setNickname(request.getNickname());
        return CommonResult.success(userService.updateById(user));
    }

    /**
     * 获取用户个人资料
     *  @author kepler
     * @since 2020-04-28
     */
    @ApiOperation(value = "获取个人资料")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public CommonResult<UserCenterResponse> getUserCenter(){
        return CommonResult.success(userService.getUserCenter());
    }

    /**
     * 获取用户个人资料
     *  @author kepler
     * @since 2020-04-28
     */
    @ApiOperation(value = "当前登录用户信息")
    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public CommonResult<User> getInfo(){
        return CommonResult.success(userService.getUserPromoter());
    }

    /**
     * 绑定手机号
     *  @author kepler
     * @since 2020-04-28
     */
    @ApiOperation(value = "绑定手机号")
    @RequestMapping(value = "/binding", method = RequestMethod.POST)
    public CommonResult<Boolean> bind(@RequestBody @Validated UserBindingRequest request){
        return CommonResult.success(userService.bind(request));
    }

    /**
     * 用户中心菜单
     *  @author kepler
     * @since 2020-04-28
     */
    @ApiOperation(value = "获取个人中心菜单")
    @RequestMapping(value = "/menu/user", method = RequestMethod.GET)
    public CommonResult<HashMap<String, Object>> getMenuUser(){

        HashMap<String, Object> map = new HashMap<>();
        map.put("routine_my_menus", systemGroupDataService.getListMapByGid(Constants.GROUP_DATA_ID_USER_CENTER_MENU));
        map.put("routine_my_banner", systemGroupDataService.getListMapByGid(Constants.GROUP_DATA_ID_USER_CENTER_BANNER));
        return CommonResult.success(map);
    }

    /**
     * 推广数据接口(昨天的佣金 累计提现金额 当前佣金)
     *  @author kepler
     * @since 2020-06-08
     */
    @ApiOperation(value = "推广数据接口(昨天的佣金 累计提现金额 当前佣金)")
    @RequestMapping(value = "/commission", method = RequestMethod.GET)
    public CommonResult<UserCommissionResponse> getCommission(){
        return CommonResult.success(userCenterService.getCommission());
    }

//    /**
//     * 推广佣金明细
//     *  @author kepler
//     * @since 2020-06-08
//     */
//    @ApiOperation(value = "推广佣金明细")
//    @RequestMapping(value = "/spread/commission/{type}", method = RequestMethod.GET)
//    @ApiImplicitParam(name = "type", value = "类型 佣金类型|0=全部,1=消费,2=充值,3=返佣,4=提现", allowableValues = "range[0,1,2,3，4]", dataType = "int")
//    public CommonResult<CommonPage<UserSpreadCommissionResponse>> getSpreadCommissionByType(@PathVariable int type, @Validated PageParamRequest pageParamRequest){
//        return CommonResult.success(CommonPage.restPage(userCenterService.getSpreadCommissionByType(type, pageParamRequest)));
//    }

    /**
     * 推广佣金明细
     */
    @ApiOperation(value = "推广佣金明细")
    @RequestMapping(value = "/spread/commission/detail", method = RequestMethod.GET)
    public CommonResult<CommonPage<SpreadCommissionDetailResponse>> getSpreadCommissionDetail(@Validated PageParamRequest pageParamRequest){
        PageInfo<SpreadCommissionDetailResponse> commissionDetail = userCenterService.getSpreadCommissionDetail(pageParamRequest);
        return CommonResult.success(CommonPage.restPage(commissionDetail));
    }

    /**
     * 推广佣金/提现总和
     *  @author kepler
     * @since 2020-06-08
     */
    @ApiOperation(value = "推广佣金/提现总和")
    @RequestMapping(value = "/spread/count/{type}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "type", value = "类型 佣金类型3=佣金,4=提现", allowableValues = "range[3,4]", dataType = "int")
    public CommonResult<Map<String, BigDecimal>> getSpreadCountByType(@PathVariable int type){
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("count", userCenterService.getSpreadCountByType(type));
        return CommonResult.success(map);
    }

    /**
     * 提现申请
     *  @author kepler
     * @since 2020-06-08
     */
    @ApiOperation(value = "提现申请")
    @RequestMapping(value = "/extract/cash", method = RequestMethod.POST)
    public CommonResult<Boolean> extractCash(@RequestBody @Validated UserExtractRequest request){
        return CommonResult.success(userCenterService.extractCash(request));
    }

    /**
     * 提现记录
     * @author kepler
     * @since 2020-10-27
     * @return
     */
    @ApiOperation(value = "提现记录")
    @RequestMapping(value = "/extract/record", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserExtractRecordResponse>> getExtractRecord(@Validated PageParamRequest pageParamRequest){
        return CommonResult.success(CommonPage.restPage(userCenterService.getExtractRecord(pageParamRequest)));
    }

    /**
     * 提现总金额
     * @author kepler
     * @since 2020-10-27
     * @return
     */
    @ApiOperation(value = "提现总金额")
    @RequestMapping(value = "/extract/totalMoney", method = RequestMethod.GET)
    public CommonResult<Map<String, BigDecimal>> getTotalMoney(){
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("count", userCenterService.getExtractTotalMoney());
        return CommonResult.success(map);
    }

    /**
     * 提现银行/提现最低金额
     *  @author kepler
     * @since 2020-06-08
     */
    @ApiOperation(value = "提现银行/提现最低金额")
    @RequestMapping(value = "/extract/bank", method = RequestMethod.GET)
    public CommonResult<UserExtractCashResponse> minExtractCash(){
        return CommonResult.success(userCenterService.minExtractCash());
    }

    /**
     * 会员等级列表
     *  @author kepler
     * @since 2020-05-18
     */
    @ApiOperation(value = "会员等级列表")
    @RequestMapping(value = "/user/level/grade", method = RequestMethod.GET)
    public CommonResult<CommonPage<SystemUserLevel>>  getUserLevelList(){
        return CommonResult.success(CommonPage.restPage(userCenterService.getUserLevelList()));
    }

    /**
     * 推广用户
     *  @author kepler
     * @since 2020-05-18
     */
    @ApiOperation(value = "推广用户")
    @RequestMapping(value = "/spread/people", method = RequestMethod.GET)
    public CommonResult<UserSpreadPeopleResponse>  getSpreadPeopleList(@Validated UserSpreadPeopleRequest request, @Validated PageParamRequest pageParamRequest){
        return CommonResult.success(userCenterService.getSpreadPeopleList(request, pageParamRequest));
    }

    /**
     * 积分记录
     *  @author kepler
     * @since 2020-05-18
     */
    @ApiOperation(value = "积分记录")
    @RequestMapping(value = "/integral/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserBill>>  getIntegralList(@Validated PageParamRequest pageParamRequest){
        return CommonResult.success(CommonPage.restPage(userCenterService.getUserBillList(Constants.USER_BILL_CATEGORY_INTEGRAL, pageParamRequest)));
    }

    /**
     * 经验记录
     *  @author kepler
     * @since 2020-05-18
     */
    @ApiOperation(value = "经验记录")
    @RequestMapping(value = "/user/expList", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserBill>>  getExperienceList(@Validated PageParamRequest pageParamRequest){
        return CommonResult.success(CommonPage.restPage(userCenterService.getUserBillList(Constants.USER_BILL_CATEGORY_EXPERIENCE, pageParamRequest)));
    }

    /**
     * 用户资金统计
     *  @author kepler
     * @since 2020-06-10
     */
    @ApiOperation(value = "用户资金统计")
    @RequestMapping(value = "/user/balance", method = RequestMethod.GET)
    public CommonResult<UserBalanceResponse>  getUserBalance(){
        return CommonResult.success(userCenterService.getUserBalance());
    }

    /**
     * 推广订单
     *  @author kepler
     * @since 2020-06-10
     */
    @ApiOperation(value = "推广订单")
    @RequestMapping(value = "/spread/order", method = RequestMethod.GET)
    public CommonResult<UserSpreadOrderResponse>  getSpreadOrder(@Validated PageParamRequest pageParamRequest){
        return CommonResult.success(userCenterService.getSpreadOrder(pageParamRequest));
    }

    /**
     * 推广人排行
     * @return List<User>
     */
    @ApiOperation(value = "推广人排行")
    @RequestMapping(value = "rank", method = RequestMethod.GET)
    public CommonResult<List<User>> getTopSpreadPeopleListByDate(@RequestParam(required = false) String type, @Validated PageParamRequest pageParamRequest){
        return CommonResult.success(userCenterService.getTopSpreadPeopleListByDate(type, pageParamRequest));
    }

    /**
     * 佣金排行
     * @return 优惠券集合
     */
    @ApiOperation(value = "佣金排行")
    @RequestMapping(value = "brokerage_rank", method = RequestMethod.GET)
    public CommonResult<List<User>> getTopBrokerageListByDate(@RequestParam String type, @Validated PageParamRequest pageParamRequest){
        return CommonResult.success(userCenterService.getTopBrokerageListByDate(type, pageParamRequest));
    }

    /**
     * 当前用户在佣金排行第几名
     * @return 优惠券集合
     */
    @ApiOperation(value = "当前用户在佣金排行第几名")
    @RequestMapping(value = "/user/brokerageRankNumber", method = RequestMethod.GET)
    public CommonResult<Integer> getNumberByTop(@RequestParam String type){
        return CommonResult.success(userCenterService.getNumberByTop(type));
    }

    /**
     * 海报背景图
     *  @author kepler
     * @since 2020-06-10
     */
    @ApiOperation(value = "推广海报图")
    @RequestMapping(value = "/user/spread/banner", method = RequestMethod.GET)
    public CommonResult<List<UserSpreadBannerResponse>>  getSpreadBannerList(@Validated PageParamRequest pageParamRequest){
        return CommonResult.success(userCenterService.getSpreadBannerList(pageParamRequest));
    }
}



