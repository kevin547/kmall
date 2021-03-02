package com.jjsj.mall.finance.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 提现数据用于分销

 */
@Data
public class UserExtractResponse {
    // 提现数据总额
    @ApiModelProperty(value = "体现数据总额")
    private BigDecimal extractCountPrice;
    // 提现次数
    @ApiModelProperty(value = "提现次数")
    private Integer extractCountNum;
    // 提现用户id
//    private Integer euid;
}
