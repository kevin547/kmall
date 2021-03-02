package com.jjsj.mall.payment.wechat;

import com.jjsj.mall.finance.model.UserRecharge;
import com.jjsj.mall.payment.vo.wechat.CreateOrderResponseVo;
import com.jjsj.mall.payment.vo.wechat.PayParamsVo;
import com.jjsj.mall.store.model.StoreOrder;

import java.util.Map;

/**
 * 微信支付

 */
public interface WeChatPayService {
    CreateOrderResponseVo create(PayParamsVo payParamsVo);

    /**
     * 微信预下单接口
     * @param storeOrder 订单
     * @param ip      ip
     * @return 获取wechat.requestPayment()参数
     */
    Map<String, String> unifiedorder(StoreOrder storeOrder, String ip);

    /**
     * 查询支付结果
     * @param orderNo 订单编号
     * @return
     */
    Boolean queryPayResult(String orderNo);

    /**
     * 微信充值预下单接口
     * @param userRecharge 充值订单
     * @param clientIp      ip
     * @return 获取wechat.requestPayment()参数
     */
    Map<String, String> unifiedRecharge(UserRecharge userRecharge, String clientIp);
}
