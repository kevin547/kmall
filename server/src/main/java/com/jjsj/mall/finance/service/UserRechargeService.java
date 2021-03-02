package com.jjsj.mall.finance.service;

import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageInfo;
import com.jjsj.mall.finance.response.UserRechargeResponse;
import com.jjsj.mall.finance.model.UserRecharge;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.finance.request.UserRechargeRefundRequest;
import com.jjsj.mall.finance.request.UserRechargeSearchRequest;
import com.jjsj.mall.front.request.UserRechargeRequest;

import java.math.BigDecimal;
import java.util.HashMap;

/**
* UserRechargeService 接口

*/
public interface UserRechargeService extends IService<UserRecharge> {

    PageInfo<UserRechargeResponse> getList(UserRechargeSearchRequest request, PageParamRequest pageParamRequest);

    HashMap<String, BigDecimal> getBalanceList();

    UserRecharge getInfoByEntity(UserRecharge userRecharge);

    UserRecharge create(UserRechargeRequest request);

    Boolean complete(UserRecharge userRecharge);

    BigDecimal getSumBigDecimal(Integer uid);

    /**
     * 充值退款
     * @param request 退款参数
     * @return
     */
    Boolean refund(UserRechargeRefundRequest request);
}
