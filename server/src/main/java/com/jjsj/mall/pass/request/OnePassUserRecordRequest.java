package com.jjsj.mall.pass.request;

import com.jjsj.constants.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 一号通用量记录请求对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "OnePassUserRecordRequest对象", description = "一号通用量记录请求对象")
public class OnePassUserRecordRequest {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录类型:sms,短信;copy,产品复制;expr_query,物流查询;expr_dump,电子面单", required = true)
    @NotBlank(message = "记录类型不能为空")
    private String type;

    @ApiModelProperty(value = "短信状态(短信类型时必传)：0发送中，1成功，2失败，3全部")
    private Integer status;

    @ApiModelProperty(value = "页码", example = Constants.DEFAULT_PAGE + "")
    private int page = Constants.DEFAULT_PAGE;

    @ApiModelProperty(value = "每页数量", example = Constants.DEFAULT_LIMIT + "")
    private int limit = Constants.DEFAULT_LIMIT;

}
