package com.jjsj.mall.front.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 推广订单
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserSpreadOrderResponse对象", description="推广订单")
public class UserSpreadOrderResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "累计推广订单")
    private Long count = 0L;

    @ApiModelProperty(value = "推广人列表")
    private List<UserSpreadOrderItemResponse> list;
}
