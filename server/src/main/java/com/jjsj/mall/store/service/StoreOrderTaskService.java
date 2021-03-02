package com.jjsj.mall.store.service;


import com.jjsj.mall.store.model.StoreOrder;

/**
 * 订单任务服务

 */
public interface StoreOrderTaskService {

    Boolean cancelByUser(StoreOrder storeOrder);

    Boolean complete(StoreOrder storeOrder);

    Boolean takeByUser(StoreOrder storeOrder);

    Boolean deleteByUser(StoreOrder storeOrder);

    Boolean refundOrder(StoreOrder storeOrder);

    Boolean paySuccessAfter(StoreOrder storeOrder);

    Boolean autoCancel(StoreOrder storeOrder);

    Boolean orderReceiving(Integer orderId);
}
