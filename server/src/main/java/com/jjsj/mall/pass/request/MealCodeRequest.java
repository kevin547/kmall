package com.jjsj.mall.pass.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 套餐购买请求对象 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "MealCodeRequest对象", description = "套餐购买请求对象")
public class MealCodeRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "套餐ID", required = true)
    @NotNull(message = "套餐ID不能为空")
    private Integer mealId;

    @ApiModelProperty(value = "套餐金额", required = true)
    @NotNull(message = "套餐金额不能为空")
    private BigDecimal price;

    @ApiModelProperty(value = "套餐量", required = true)
    @NotNull(message = "套餐量不能为空")
    private Integer num;

    @ApiModelProperty(value = "套餐类型:sms,短信;copy,产品复制;expr_query,物流查询;expr_dump,电子面单", required = true)
    @NotBlank(message = "套餐类型不能为空")
    private String type;

    @ApiModelProperty(value = "支付类型 weixin：微信支付/alipay：支付宝支付", required = true)
    @NotBlank(message = "支付类型不能为空")
    private String payType;

}
