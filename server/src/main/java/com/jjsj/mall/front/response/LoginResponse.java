package com.jjsj.mall.front.response;

import com.jjsj.mall.user.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Login Response
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "LoginResponse", description = "用户登录返回数据")
public class LoginResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户登录密钥")
    private String token;

    @ApiModelProperty(value = "用户登录密钥到期时间")
    private Date expiresTime;

    @ApiModelProperty(value = "user对象")
    private User user;

}
