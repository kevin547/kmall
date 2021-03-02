package com.jjsj.mall.front.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户编辑Request 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UserEditRequest对象", description = "修改个人资料")
public class UserEditRequest implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "用户昵称")
    @NotBlank(message = "请填写用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    @NotBlank(message = "请上传用户头像")
    private String avatar;
}
