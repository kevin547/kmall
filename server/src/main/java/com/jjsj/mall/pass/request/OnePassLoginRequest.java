package com.jjsj.mall.pass.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 一号通用户登录请求对象 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "OnePassLoginRequest对象", description = "一号通用户登录请求对象")
public class OnePassLoginRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号", required = true)
    @NotBlank(message = "账号不能为空")
    private String account;

    @ApiModelProperty(value = "password", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;
}
