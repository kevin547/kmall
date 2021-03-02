package com.jjsj.mall.export.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 砍价商品ExeclVo对象

 */
@Data
public class BargainProductExcelVo {

    @ApiModelProperty(value = "砍价活动名称")
    private String title;

    @ApiModelProperty(value = "砍价活动简介")
    private String info;

    @ApiModelProperty(value = "砍价金额")
    private String price;

    @ApiModelProperty(value = "用户每次砍价的次数")
    private Integer bargainNum;

    @ApiModelProperty(value = "砍价状态 0(到砍价时间不自动开启)  1(到砍价时间自动开启时间)")
    private String status;

    @ApiModelProperty(value = "砍价开启时间")
    private String startTime;

    @ApiModelProperty(value = "砍价结束时间")
    private String stopTime;

    @ApiModelProperty(value = "销量")
    private Integer sales;

    @ApiModelProperty(value = "库存剩余")
    private Integer quotaShow;

    @ApiModelProperty(value = "反多少积分")
    private BigDecimal giveIntegral;

    @ApiModelProperty(value = "添加时间")
    private String addTime;
}
