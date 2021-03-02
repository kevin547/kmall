package com.jjsj.mall.front.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 价格计算集合 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "PriceGroupResponse对象", description = "价格计算集合对象")
public class PriceGroupResponse {

    @ApiModelProperty(value = "运费")
    private BigDecimal storePostage;

    @ApiModelProperty(value = "满额包邮")
    private BigDecimal storeFreePostage;

    @ApiModelProperty(value = "总金额")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "原价")
    private BigDecimal costPrice;

    @ApiModelProperty(value = "会员金额")
    private BigDecimal vipPrice;

    @ApiModelProperty(value = "优惠券金额")
    private BigDecimal couponPrice;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal payPrice;

    @ApiModelProperty(value = "邮费金额")
    private BigDecimal payPostage;

    @ApiModelProperty(value = "抵扣金额")
    private BigDecimal deductionPrice;

    @ApiModelProperty(value = "使用积分")
    private Integer usedIntegral;
}
