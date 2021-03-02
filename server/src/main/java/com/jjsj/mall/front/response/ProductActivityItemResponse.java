package com.jjsj.mall.front.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品所参与的活动类型

 */
@Data
public class ProductActivityItemResponse {

    @ApiModelProperty(value = "参与活动id")
    private Integer id;

    @ApiModelProperty(value = "秒杀结束时间")
    private Integer time;

    @ApiModelProperty(value = "活动参与类型")
    private String type;
}
