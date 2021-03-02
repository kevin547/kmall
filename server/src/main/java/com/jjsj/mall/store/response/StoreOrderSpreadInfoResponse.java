package com.jjsj.mall.store.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="StoreOrderSpreadInfoResponse对象", description="推广人信息")
public class StoreOrderSpreadInfoResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "推广人id")
    private Integer id = 0;

    @ApiModelProperty(value = "推广人姓名")
    private String name = "";


}
