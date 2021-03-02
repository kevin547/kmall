package com.jjsj.mall.front.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 购物车重选Request对象类 
 */
@Data
public class CartResetRequest {

    @ApiModelProperty(value = "购物车id")
    @NotNull(message = "id 不能为空")
    private Long id;

    @ApiModelProperty(value = "购物车数量")
    @NotNull(message = "num 不能为空")
    private Integer num;

    @ApiModelProperty(value = "商品id")
    @NotNull(message = "productId 不能为空")
    private Integer productId;

    @ApiModelProperty(value = "AttrValue Id")
    @NotNull(message = "unique 不能为空")
    private Integer unique;
}
