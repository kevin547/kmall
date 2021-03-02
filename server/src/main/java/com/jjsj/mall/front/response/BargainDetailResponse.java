package com.jjsj.mall.front.response;

import com.jjsj.mall.store.response.StoreProductResponse;
import com.jjsj.mall.user.model.User;
import lombok.Data;

/**
 * 砍价商品详情信息Response

 */
@Data
public class BargainDetailResponse {

    /**
     * 砍价商品信息
     */
    private StoreProductResponse bargain;

    /**
     * 用户砍价状态
     */
    private Integer userBargainStatus;

    /**
     * 用户信息
     */
    private User userInfo;

    /**
     * 砍价单属性AttrValueId
     */
    private Integer aloneAttrValueId;

    /**
     * 砍价支付成功订单数量
     */
    private Integer bargainSumCount;

    public BargainDetailResponse() {
    }

    public BargainDetailResponse(StoreProductResponse bargain, Integer userBargainStatus,
        User userInfo, Integer bargainSumCount) {
        this.bargain = bargain;
        this.userBargainStatus = userBargainStatus;
        this.userInfo = userInfo;
        this.bargainSumCount = bargainSumCount;
    }

}
