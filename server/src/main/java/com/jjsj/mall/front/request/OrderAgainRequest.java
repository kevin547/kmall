package com.jjsj.mall.front.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * OrderAgainRequest

 */
@Data
public class OrderAgainRequest {

    @ApiModelProperty(value = "订单编号")
    @NotNull(message = "订单编号不能为空")
    private String orderNo;
}
