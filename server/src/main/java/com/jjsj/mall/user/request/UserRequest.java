package com.jjsj.mall.user.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户表 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_user")
@ApiModel(value = "UserRequest对象", description = "用户表")
public class UserRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "uid")
    private Integer uid;

    @ApiModelProperty(value = "真实姓名")
    @NotNull
    private String realName;

    @ApiModelProperty(value = "生日")
    @NotNull
    private String birthday;

    @ApiModelProperty(value = "身份证号码")
    @NotNull
    private String cardId;

    @ApiModelProperty(value = "用户备注")
    private String mark;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "状态是否正常， 0 = 禁止， 1 = 正常")
    @NotNull
    private Boolean status;

    @ApiModelProperty(value = "详细地址")
    private String addres;

    @ApiModelProperty(value = "等级")
    private Integer level;

    @ApiModelProperty(value = "用户分组id")
    private String groupId;

    @ApiModelProperty(value = "用户标签id")
    private String tagId;

    @ApiModelProperty(value = "是否为推广员")
    private Boolean isPromoter;

}
