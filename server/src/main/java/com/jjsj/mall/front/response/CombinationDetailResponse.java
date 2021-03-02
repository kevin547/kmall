package com.jjsj.mall.front.response;

import com.jjsj.mall.combination.response.StoreCombinationInfoResponse;
import com.jjsj.mall.combination.response.StorePinkResponse;
import com.jjsj.mall.store.model.StoreProductReply;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 拼团商品响应体

 */
@Data
public class CombinationDetailResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "拼团团长ID列表")
    private List<Integer> pindAll;

    @ApiModelProperty(value = "拼团列表")
    private List<StorePinkResponse> pink;

    @ApiModelProperty(value = "拼团成功列表")
    private List<StorePinkResponse> pinkOkList;

    @ApiModelProperty(value = "拼团完成的商品总件数")
    private Integer pinkOkSum;

    @ApiModelProperty(value = "拼团商品信息")
    private StoreCombinationInfoResponse storeInfo;

    @ApiModelProperty(value = "回复")
    private List<StoreProductReply> reply;

    @ApiModelProperty(value = "好评率")
    private Integer replyChance;

    @ApiModelProperty(value = "评论数量")
    private Integer replyCount;

    @ApiModelProperty(value = "商品规格")
    private List<HashMap<String,Object>> productAttr;

    @ApiModelProperty(value = "商品规格值")
//    private List<StoreProductAttrValue> productValue;
    private HashMap<String,Object> productValue;

    @ApiModelProperty(value = "商品单双规格")
    private Boolean specType;

    @ApiModelProperty(value = "拼团单属性AttrValueId")
    private Integer aloneAttrValueId;
}
