package com.jjsj.mall.wechat.service;

import com.jjsj.mall.wechat.vo.WechatSendMessageForCash;
import com.jjsj.mall.wechat.vo.WechatSendMessageForCreateOrder;
import com.jjsj.mall.wechat.vo.WechatSendMessageForDistrbution;
import com.jjsj.mall.wechat.vo.WechatSendMessageForGetPackage;
import com.jjsj.mall.wechat.vo.WechatSendMessageForIntegral;
import com.jjsj.mall.wechat.vo.WechatSendMessageForOrderCancel;
import com.jjsj.mall.wechat.vo.WechatSendMessageForPackage;
import com.jjsj.mall.wechat.vo.WechatSendMessageForPaySuccess;
import com.jjsj.mall.wechat.vo.WechatSendMessageForReFundEd;
import com.jjsj.mall.wechat.vo.WechatSendMessageForReFundNotify;
import com.jjsj.mall.wechat.vo.WechatSendMessageForTopped;
import com.jjsj.mall.wechat.vo.WechatSendMessageForVerSuccess;
import com.jjsj.mall.wechat.vo.*;

/**
 *  根据已申请的模版发送小程序消息

 */
public interface WechatSendMessageForMinService {
    /**
     *  发送积分到账消息
     * @param integral  待发送积分参数
     * @param userId    接收用户id
     */
    void sendIntegralMessage(WechatSendMessageForIntegral integral, Integer userId);

    /**
     * 退款申请通知
     * @param reFundNotify  退款申请参数
     * @param userId        接收者用户Id
     */
    void sendReFundNotifyMessage(WechatSendMessageForReFundNotify reFundNotify, Integer userId);

    /**
     * 退款通知
     * @param reFundEd      退款参数
     * @param userId        接收者用户Id
     */
    void sendReFundEdMessage(WechatSendMessageForReFundEd reFundEd, Integer userId);

    /**
     *  付款成功通知
     * @param paySuccess    付款成功参数
     * @param userId        接受者用户Id
     */
    void sendPaySuccessMessage(WechatSendMessageForPaySuccess paySuccess, Integer userId);

    /**
     * 充值成功过通知
     * @param topped    充值成功通知
     * @param userId    接收者Id
     */
    void sendToppedMessage(WechatSendMessageForTopped topped, Integer userId);

    /**
     * 配送通知
     * @param distrbution   配送参数
     * @param userId        接收者Id
     */
    void sendDistrbutionMessage(WechatSendMessageForDistrbution distrbution, Integer userId);

    /**
     * 提现通知
     * @param cash      提现参数
     * @param userId    用户Id
     */
    void sendCashMessage(WechatSendMessageForCash cash, Integer userId);

    /**
     * 订单创建
     * @param cateOrder 创建订单参数
     * @param userId    用户Id
     */
    void sendCreateOrderMessage(WechatSendMessageForCreateOrder cateOrder, Integer userId);

    /**
     * 收货通知
     * @param getPackage    收货参数
     * @param userId        用户Id
     */
    void sendGetPackageMessage(WechatSendMessageForGetPackage getPackage, Integer userId);

    /**
     * 取消订单
     * @param orderCancel   取消订单参数
     * @param userId    用户Id
     */
    void sendOrderCancelMessage(WechatSendMessageForOrderCancel orderCancel, Integer userId);

    /**
     * 发货通知
     * @param toPackage     发货通知
     * @param userId        用户id
     */
    void sendRePackageMessage(WechatSendMessageForPackage toPackage, Integer userId);

    /**
     * 核销通知
     * @param ver     核销参数
     * @param userId        用户Id
     */
    void sendVerSuccessMessage(WechatSendMessageForVerSuccess ver, Integer userId);


}
