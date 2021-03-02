package com.jjsj.mall.store.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * 商品属性值表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_product_attr_value")
@ApiModel(value="StoreProductAttrValueListRequest对象", description="商品属性值")
public class StoreProductAttrValueListRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Valid
    @ApiModelProperty(value = "商品属性值")
    private List<StoreProductAttrValueRequest> storeProductAttrValueRequest;


}
