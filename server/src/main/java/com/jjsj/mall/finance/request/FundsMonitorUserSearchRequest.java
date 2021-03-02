package com.jjsj.mall.finance.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 资金监控

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="FundsMonitorRequest对象", description="资金监控")
public class FundsMonitorUserSearchRequest implements Serializable {

    @ApiModelProperty(value = "搜索关键字")
    private String keywords;

    @ApiModelProperty(value = "类型")
    private String type;
    private String endTime;

    @ApiModelProperty(value = "最大佣金")
    private BigDecimal max;

    @ApiModelProperty(value = "最小佣金")
    private BigDecimal min;

    @ApiModelProperty(value = "排序 asc/desc")
    private String sort;
}
