package com.jjsj.mall.front.response;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户地址表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UserRechargeResponse对象", description = "c")
public class UserRechargeResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户的唯一标识")
    @TableField(value = "openId")
    private List<UserRechargeItemResponse> rechargeQuota;

    @ApiModelProperty(value = "注意事项")
    private List<String> rechargeAttention;
}
