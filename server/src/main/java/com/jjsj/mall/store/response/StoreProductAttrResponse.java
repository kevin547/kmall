package com.jjsj.mall.store.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 商品属性表

 */
@Data
@ApiModel(value="StoreProductAttrResponse对象", description="商品属性表Response")
public class StoreProductAttrResponse implements Serializable {

    private static final long serialVersionUID=1L;


    //    @ApiModelProperty(value = "attrId")
//    @TableId(value = "id", type = IdType.AUTO)
//    private Integer id;

    @ApiModelProperty(value = "商品ID")
    private Integer productId;

    @ApiModelProperty(value = "属性名")
    private String attrName;

    @ApiModelProperty(value = "属性值") // 将原有的数据逗号分割为数组
    private String attrValues;

    @ApiModelProperty(value = "活动类型 0=商品，1=秒杀，2=砍价，3=拼团")
    private Integer type;

    @ApiModelProperty(value = "属性和优惠券使用状态")
    private List<StoreProductAttrValueItemResponse> attrValue;

    /**
     * 手动设置attrValues属性
     * @param attrValuesPram string参数
     * @return 转换后的List<String>集合
     */
    public List<String> setAttrValuesManual(String attrValuesPram){
        List<String> strings = new ArrayList<>();
        if(attrValuesPram.indexOf(",") > 0){
            strings.addAll(Arrays.asList(attrValuesPram.split(",")));
        }else{
            strings.add(attrValuesPram);
        }
        return strings;
    }

}
