package com.jjsj.mall.store.service;


/**
 * 订单任务服务 StoreOrderService 接口

 */
 public interface OrderTaskService{

     void cancelByUser();

     void refundApply();

     void complete();

     void takeByUser();

     void deleteByUser();

    void orderPaySuccessAfter();

    /**
     * 自动取消未支付订单
     */
    void autoCancel();

    /**
     * 订单收货
     */
    void orderReceiving();

    /**
     * 订单自动完成
     */
    void autoComplete();
}
