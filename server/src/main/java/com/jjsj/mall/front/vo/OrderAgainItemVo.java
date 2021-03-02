package com.jjsj.mall.front.vo;

import lombok.Data;

/**
 * 再次下单ItemVO对象 
 */
@Data
public class OrderAgainItemVo {

    public OrderAgainItemVo() {
    }

    public OrderAgainItemVo(Integer type, String title, String msg) {
        this.type = type;
        this.title = title;
        this.msg = msg;
    }

    private Integer type;
    private String title;
    private String msg;
    private String payType;
    private String deliveryType;

}
