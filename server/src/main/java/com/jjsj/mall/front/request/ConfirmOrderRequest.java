package com.jjsj.mall.front.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 确认订单请求对象
 */
@Data
public class ConfirmOrderRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "购物车id集合")
    @NotNull(message = "购物车编号集合 不能为空")
    private String cartIds;

    @ApiModelProperty(value = "是否立即购买")
    @NotNull(message = "是否立即购买 不能为空")
    private Boolean isNew;

    @ApiModelProperty(value = "是否再一下单")
    @NotNull(message = "是否再一下单 不能为空")
    private Boolean addAgain;

    @ApiModelProperty(value = "是否秒杀商品")
    @NotNull(message = "是否秒杀商品 不能为空")
    private Boolean secKill;

    @ApiModelProperty(value = "是否砍价商品")
    @NotNull(message = "是否砍价商品 不能为空")
    private Boolean bargain;

    @ApiModelProperty(value = "是否拼团商品")
    @NotNull(message = "是否拼团商品 不能为空")
    private Boolean combination;

    @ApiModelProperty(value = "地址id")
//    @NotNull(message = "地址编号 不能为空")
    private Integer addressId;
}
