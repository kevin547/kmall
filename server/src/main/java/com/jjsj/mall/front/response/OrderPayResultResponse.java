package com.jjsj.mall.front.response;

import com.jjsj.mall.front.vo.WxPayJsResultVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单支付结果 Response 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "OrderPayResultResponse对象", description = "订单支付结果响应对象")
public class OrderPayResultResponse {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付状态")
    private Boolean status;

    @ApiModelProperty(value = "微信调起支付参数对象")
    private WxPayJsResultVo jsConfig;

    @ApiModelProperty(value = "支付类型")
    private String payType;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;
}
