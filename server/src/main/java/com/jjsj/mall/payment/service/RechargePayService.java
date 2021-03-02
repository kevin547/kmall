package com.jjsj.mall.payment.service;

import com.jjsj.mall.finance.model.UserRecharge;
import com.jjsj.mall.payment.vo.wechat.CreateOrderResponseVo;

/**
 * 订单支付

 */
public interface RechargePayService {
    CreateOrderResponseVo payOrder(Integer orderId, String payType, String clientIp);

    boolean success(String orderId, Integer userId, String payType);

    /**
     * 支付成功处理
     * @param userRecharge 充值订单
     */
    Boolean paySuccess(UserRecharge userRecharge);
}
