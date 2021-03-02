package com.jjsj.mall.system.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单状态图片

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SystemGroupDataOrderStatusPicResponse对象", description="订单状态图片")
public class SystemGroupDataOrderStatusPicResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "订单状态")
    private int orderStatus;

    @ApiModelProperty(value = "图片地址")
    private String url;

}
