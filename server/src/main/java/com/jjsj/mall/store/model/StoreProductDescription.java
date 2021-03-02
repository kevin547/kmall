package com.jjsj.mall.store.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商品描述表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_product_description")
@ApiModel(value="StoreProductDescription对象", description="商品描述表")
public class StoreProductDescription implements Serializable {

    private static final long serialVersionUID=1L;

    public StoreProductDescription() {
    }

    public StoreProductDescription(Integer productId, String description,Integer type) {
        this.productId = productId;
        this.description = description;
        this.type = type;
    }

    @ApiModelProperty(value = "商品ID")
    private Integer productId;

    @ApiModelProperty(value = "商品详情")
    private String description;

    @ApiModelProperty(value = "商品类型")
    private Integer type;


}
