package com.jjsj.mall.sms.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 短信API登录
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SmsLoginRequest对象", description = "短信登录")
public class SmsLoginRequest {

    @ApiModelProperty(value = "账号", required = true)
    private String account;

    @ApiModelProperty(value = "smsToken", required = true)
    private String token;
}
