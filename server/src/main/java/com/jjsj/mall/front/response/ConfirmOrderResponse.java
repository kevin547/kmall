package com.jjsj.mall.front.response;

import com.jjsj.mall.marketing.response.StoreCouponUserResponse;
import com.jjsj.mall.store.response.StoreCartResponse;
import com.jjsj.mall.user.model.User;
import com.jjsj.mall.user.model.UserAddress;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单确认ApiResponse对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ConfirmOrderResponse对象", description = "订单确认相应对象")
public class ConfirmOrderResponse {

    @ApiModelProperty(value = "收否扣减")
    private Boolean deduction;

    @ApiModelProperty(value = "可用优惠券")
    private StoreCouponUserResponse usableCoupon;

    @ApiModelProperty(value = "用户地址")
    private UserAddress addressInfo;

    @ApiModelProperty(value = "购物车信息")
    private List<StoreCartResponse> cartInfo;

    @ApiModelProperty(value = "价格集合")
    private PriceGroupResponse priceGroup;

    @ApiModelProperty(value = "其他")
    private HashMap<String, Object> other;

    @ApiModelProperty(value = "订单key")
    private String orderKey;

    @ApiModelProperty(value = "线下邮费")
    private String offlinePostage;

    @ApiModelProperty(value = "用户信息")
    private User userInfo;

    @ApiModelProperty(value = "积分抵扣比例")
    private String integralRatio;

    @ApiModelProperty(value = "线下支付状态")
    private String offlinePayStatus;

    @ApiModelProperty(value = "余额支付 1 开启 2 关闭")
    private String yuePayStatus;

    @ApiModelProperty(value = "门店自提是否开启")
    private String storeSelfMention;

    @ApiModelProperty(value = "门店信息")
    private String systemStore;

    @ApiModelProperty(value = "微信支付 1 开启 0 关闭")
    private String payWeixinOpen;

    @ApiModelProperty(value = "秒杀id")
    private Integer secKillId;

    @ApiModelProperty(value = "砍价id")
    private Integer bargainId;

    @ApiModelProperty(value = "拼团id")
    private Integer combinationId;

    @ApiModelProperty(value = "团长id")
    private Integer pinkId;
}
