package com.jjsj.mall.payment.service;

import com.jjsj.mall.front.request.OrderPayRequest;
import com.jjsj.mall.front.response.OrderPayResultResponse;
import com.jjsj.mall.payment.vo.wechat.CreateOrderResponseVo;
import com.jjsj.mall.store.model.StoreOrder;

/**
 * 订单支付

 */
public interface OrderPayService{
    CreateOrderResponseVo payOrder(Integer orderId, String fromType, String clientIp);

    boolean success(String orderId, Integer userId, String payType);

    void afterPaySuccess();

    /**
     * 支付成功处理
     * @param storeOrder 订单
     */
    Boolean paySuccess(StoreOrder storeOrder);

    /**
     * 余额支付
     * @param StoreOrder 订单
     * @return
     */
    Boolean yuePay(StoreOrder storeOrder);

    /**
     * 订单支付
     * @param orderPayRequest 支付参数
     * @param ip    ip
     * @return
     */
    OrderPayResultResponse payment(OrderPayRequest orderPayRequest, String ip);
}
