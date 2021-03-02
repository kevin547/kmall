package com.jjsj.mall.marketing.response;

import com.jjsj.mall.category.model.Category;
import com.jjsj.mall.marketing.request.StoreCouponRequest;
import com.jjsj.mall.store.model.StoreProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 优惠券记录表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="StoreCouponInfoResponse对象", description="优惠券详情")
public class StoreCouponInfoResponse implements Serializable {

    private static final long serialVersionUID=1L;

    public StoreCouponInfoResponse(StoreCouponRequest coupon, List<StoreProduct> product, List<Category> category) {
        this.coupon = coupon;
        this.product = product;
        this.category = category;
    }

    @ApiModelProperty(value = "优惠券发布id")
    private StoreCouponRequest coupon;

    @ApiModelProperty(value = "商品信息")
    private List<StoreProduct> product;

    @ApiModelProperty(value = "分类信息")
    private List<Category> category;

}
