package com.jjsj.mall.store.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 分销用户订单数据

 */
@Data
public class RetailShopOrderDataResponse {
    // 总订单价
    @ApiModelProperty(value = "订单总价")
    private BigDecimal orderPrice;
    // 订单数量
    @ApiModelProperty(value = "订单数量")
    private Integer orderCount;
    // 用户id
//    private Integer uid;
}
