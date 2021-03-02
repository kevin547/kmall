package com.jjsj.mall.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.CommonPage;
import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageInfo;
import com.jjsj.mall.finance.request.UserExtractRequest;
import com.jjsj.mall.front.response.LoginResponse;
import com.jjsj.mall.front.response.OrderPayResultResponse;
import com.jjsj.mall.front.response.SpreadCommissionDetailResponse;
import com.jjsj.mall.front.response.UserBalanceResponse;
import com.jjsj.mall.front.response.UserCommissionResponse;
import com.jjsj.mall.front.response.UserExtractCashResponse;
import com.jjsj.mall.front.response.UserExtractRecordResponse;
import com.jjsj.mall.front.response.UserRechargeBillRecordResponse;
import com.jjsj.mall.front.response.UserRechargeResponse;
import com.jjsj.mall.front.response.UserSpreadBannerResponse;
import com.jjsj.mall.front.response.UserSpreadCommissionResponse;
import com.jjsj.mall.front.response.UserSpreadOrderResponse;
import com.jjsj.mall.front.response.UserSpreadPeopleResponse;
import com.jjsj.mall.system.model.SystemUserLevel;
import com.jjsj.mall.front.request.UserRechargeRequest;
import com.jjsj.mall.front.request.UserSpreadPeopleRequest;
import com.jjsj.mall.user.model.User;
import com.jjsj.mall.user.model.UserBill;
import com.jjsj.mall.user.request.RegisterThirdUserRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户中心 服务类 
 */
public interface UserCenterService extends IService<User> {

    UserCommissionResponse getCommission();

    PageInfo<UserSpreadCommissionResponse> getSpreadCommissionByType(int type,
        PageParamRequest pageParamRequest);

    BigDecimal getSpreadCountByType(int type);

    Boolean extractCash(UserExtractRequest request);

    UserExtractCashResponse minExtractCash();

    List<SystemUserLevel> getUserLevelList();

    UserSpreadPeopleResponse getSpreadPeopleList(UserSpreadPeopleRequest request,
        PageParamRequest pageParamRequest);

    List<UserBill> getUserBillList(String type, PageParamRequest pageParamRequest);

    UserRechargeResponse getRechargeConfig();

    UserBalanceResponse getUserBalance();

    UserSpreadOrderResponse getSpreadOrder(PageParamRequest pageParamRequest);

    OrderPayResultResponse recharge(UserRechargeRequest request);

    LoginResponse weChatAuthorizeLogin(String code, Integer spreadUid);

    String getLogo();

    LoginResponse weChatAuthorizeProgramLogin(String code, RegisterThirdUserRequest request);

    List<User> getTopSpreadPeopleListByDate(String type, PageParamRequest pageParamRequest);

    List<User> getTopBrokerageListByDate(String type, PageParamRequest pageParamRequest);

    List<UserSpreadBannerResponse> getSpreadBannerList(PageParamRequest pageParamRequest);

    Integer getNumberByTop(String type);

    Boolean transferIn(BigDecimal price);

    PageInfo<UserExtractRecordResponse> getExtractRecord(PageParamRequest pageParamRequest);

    BigDecimal getExtractTotalMoney();

    /**
     * 推广佣金明细
     *
     * @param pageParamRequest 分页参数
     */
    PageInfo<SpreadCommissionDetailResponse> getSpreadCommissionDetail(
        PageParamRequest pageParamRequest);

    /**
     * 用户账单记录（现金）
     *
     * @param type 记录类型：all-全部，expenditure-支出，income-收入
     * @return CommonPage
     */
    CommonPage<UserRechargeBillRecordResponse> nowMoneyBillRecord(String type,
        PageParamRequest pageRequest);
}
