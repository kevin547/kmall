package com.jjsj.mall.front.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 计算订单价格Request对象 
 */
@Data
public class OrderComputedRequest {

    @ApiModelProperty(value = "地址id")
    private Integer addressId;

    @ApiModelProperty(value = "优惠券id")
    private Integer couponId;

    @ApiModelProperty(value = "支付类型")
    private String payType;

    @ApiModelProperty(value = "快递类型")
    private Integer shippingType;

    @ApiModelProperty(value = "抵扣积分")
    private Boolean useIntegral;

}
