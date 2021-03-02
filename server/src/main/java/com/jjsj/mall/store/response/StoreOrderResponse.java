package com.jjsj.mall.store.response;

import com.jjsj.common.CommonPage;
import com.jjsj.mall.system.response.StoreOrderItemResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="StoreOrderCountResponse对象", description="订单数量")
public class StoreOrderResponse implements Serializable {

    @ApiModelProperty(value = "top")
    private StoreOrderTopItemResponse top;

    @ApiModelProperty(value = "状态")
    private StoreOrderCountItemResponse status;

    @ApiModelProperty(value = "列表")
    private CommonPage<StoreOrderItemResponse> list;


}
