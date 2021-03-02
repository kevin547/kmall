package com.jjsj.mall.user.response;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 推广用户响应体
 */
@Data
public class SpreadUserResponse {

    @ApiModelProperty(value = "用户id")
    private Integer uid;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "佣金金额,未提现金额")
    private BigDecimal brokeragePrice;

    @ApiModelProperty(value = "推广人id")
    private Integer spreadUid;

    @ApiModelProperty(value = "上级推广员名称")
    private String spreadNickname;

    @ApiModelProperty(value = "用户购买次数")
    private Integer payCount;

    @ApiModelProperty(value = "推广用户数")
    private Integer spreadCount;

    @ApiModelProperty(value = "推广订单数")
    private Integer spreadOrderNum;

    @ApiModelProperty(value = "推广订单额")
    private BigDecimal spreadOrderTotalPrice;

    @ApiModelProperty(value = "佣金总金额")
    private BigDecimal totalBrokeragePrice;

    @ApiModelProperty(value = "推广用户数量")
    private int spreadPeopleCount;

    @ApiModelProperty(value = "已提现金额")
    private BigDecimal extractCountPrice;

    @ApiModelProperty(value = "已提现次数")
    private Integer extractCountNum;
}
