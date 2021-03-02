package com.jjsj.mall.front.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * H5 砍价公共请求对象 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "BargainFrontRequest对象", description = "砍价公共请求对象")
public class BargainFrontRequest {

    @ApiModelProperty(value = "砍价商品ID", required = true)
    @NotNull(message = "砍价商品编号不能为空")
    private Integer bargainId;

    private Integer bargainUserUid;
}
