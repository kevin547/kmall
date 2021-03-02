package com.jjsj.mall.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

/**
 * 资金操作 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "资金操作", description = "资金操作")
public class UserOperateIntegralMoneyRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "uid")
    @NotNull
    @Min(value = 1, message = "请输入正确的uid")
    private Integer uid;

    @ApiModelProperty(value = "积分类型， 1 = 增加， 2 = 减少")
    @NotNull
    @Range(min = 1, max = 2, message = "请选择正确的类型， 【1 = 增加， 2 = 减少】")
    private int integralType;

    @ApiModelProperty(value = "积分")
    @Min(value = 0)
    @Max(value = 999999)
    private Integer integralValue;

    @ApiModelProperty(value = "余额类型， 1 = 增加， 2 = 减少")
    @NotNull
    @Range(min = 1, max = 2, message = "请选择正确的类型， 【1 = 增加， 2 = 减少】")
    private int moneyType;

    @ApiModelProperty(value = "余额")
    @DecimalMin(value = "0")
    @DecimalMax(value = "999999")
    private BigDecimal moneyValue;

}
