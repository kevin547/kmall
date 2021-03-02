package com.jjsj.mall.pass.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 一号通登录对象 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "OnePassLoginVo对象", description = "一号通登录对象")
public class OnePassLoginVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "一号通账号")
    private String account;

    /**
     * secret = md5(账号+md5(密码))
     */
    @ApiModelProperty(value = "一号通密钥")
    private String secret;

}
