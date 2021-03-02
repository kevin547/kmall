package com.jjsj.mall.front.response;

import java.math.BigDecimal;
import lombok.Data;

/**
 * 砍价商品统计Response对象

 */
@Data
public class BargainCountResponse {

    /**
     * 已砍金额
     **/
    private BigDecimal alreadyPrice;
    /**
     * 砍价人数
     **/
    private Integer count;
    /**
     * 剩余金额
     **/
    private BigDecimal price;
    /**
     * 砍价金额百分比
     **/
    private Integer pricePercent;
    /**
     * 活动状态：1参与中 2 活动结束参与失败 3活动结束参与成功
     **/
    private Integer status;
    /**
     * 用户砍价状态
     **/
    private Boolean userBargainStatus;
    /**
     * 用户帮砍次数是否消耗完
     **/
    private Boolean isConsume;

    public BargainCountResponse() {
    }

    public BargainCountResponse(BigDecimal alreadyPrice, Integer count, BigDecimal price,
        Integer pricePercent, Integer status, Boolean userBargainStatus, Boolean isConsume) {
        this.alreadyPrice = alreadyPrice;
        this.count = count;
        this.price = price;
        this.pricePercent = pricePercent;
        this.status = status;
        this.userBargainStatus = userBargainStatus;
        this.isConsume = isConsume;
    }

}
