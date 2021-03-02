package com.jjsj.mall.front.response;

import lombok.Data;

/**
 * 支付订单 Response item
 */
@Data
public class OrderPayItemResponse {

    private String key;
    private Object orderId;

    public OrderPayItemResponse() {
    }

    public OrderPayItemResponse(String key, Object orderId) {
        this.key = key;
        this.orderId = orderId;
    }
}
