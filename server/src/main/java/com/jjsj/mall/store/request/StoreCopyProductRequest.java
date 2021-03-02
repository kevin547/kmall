package com.jjsj.mall.store.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 复制商品请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="StoreCopyProductRequest对象", description="复制商品请求对象")
public class StoreCopyProductRequest {

    @ApiModelProperty(value = "复制商品地址")
    @NotBlank(message = "复制商品地址 不能为空")
    private String url;

}
