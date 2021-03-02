package com.jjsj.mall.system.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 身份管理搜索Request对象

 */
@Data
public class SystemRoleSearchRequest {
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "身份管理名称")
//    @NotNull(message = "身份管理名称不能为空")
    private String roleName;

    @ApiModelProperty(value = "身份管理权限(menus_id)")
//    @NotNull(message = "身份管理权限不能为空")
    private String rules;

    @ApiModelProperty(value = "身份管理名称")
//    @Min(value=0)
//    @NotNull(message = "level 不能为空，且为正整数")
    private Integer level;

    @ApiModelProperty(value = "状态")
    private Boolean status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
}
