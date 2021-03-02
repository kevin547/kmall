package com.jjsj.mall.front.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 获取商品评论对象
 */
@Data
public class GetProductReply {

    @ApiModelProperty(value = "商品attrid")
    @NotBlank(message = "商品uniId不能为空")
    private String uni;

    @ApiModelProperty(value = "订单id")
    @NotNull(message = "订单id不能为空")
    private Integer orderId;
}
