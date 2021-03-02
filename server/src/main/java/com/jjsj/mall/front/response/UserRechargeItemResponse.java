package com.jjsj.mall.front.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户地址表
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserRechargeResponse对象", description="c")
public class UserRechargeItemResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "充值模板id")
    private Integer id;

    @ApiModelProperty(value = "充值金额")
    private String price;

    @ApiModelProperty(value = "赠送金额")
    private String giveMoney;

}
