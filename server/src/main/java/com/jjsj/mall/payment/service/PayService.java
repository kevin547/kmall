package com.jjsj.mall.payment.service;

import com.jjsj.mall.payment.vo.wechat.CreateOrderResponseVo;

/**
 * 订单支付

 */
public abstract class PayService {
    public abstract CreateOrderResponseVo payOrder(Integer orderId, String from, String clientIp);

    public abstract boolean success(String orderId, Integer userId, String payType);
}
