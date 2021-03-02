package com.jjsj.mall.store.response;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * h5端使用 attrValueItem

 */
@Data
public class StoreProductAttrValueItemResponse {

    @ApiModelProperty(value = "属性名称")
    private String attr;

    @ApiModelProperty(value = "优惠券可用状态")
    private boolean check;// 优惠券可用状态
}
