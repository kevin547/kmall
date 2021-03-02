package com.jjsj.mall.front.service;

import com.jjsj.common.CommonPage;
import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageInfo;
import com.jjsj.mall.category.vo.CategoryTreeVo;
import com.jjsj.mall.front.response.ProductDetailResponse;
import com.jjsj.mall.front.response.ProductResponse;
import com.jjsj.mall.front.response.StoreProductReplayCountResponse;
import com.jjsj.mall.store.response.StoreProductReplyResponse;
import com.jjsj.mall.store.response.StoreProductResponse;
import com.jjsj.mall.front.request.IndexStoreProductSearchRequest;
import com.jjsj.mall.front.request.ProductRequest;
import java.util.List;

/**
 * IndexService 接口
 */
public interface ProductService {

    CommonPage<ProductResponse> getIndexProduct(IndexStoreProductSearchRequest request,
        PageParamRequest pageParamRequest);

    List<CategoryTreeVo> getCategory();

    CommonPage<ProductResponse> getList(ProductRequest request, PageParamRequest pageParamRequest);

    ProductDetailResponse getDetail(Integer id);

    PageInfo<StoreProductReplyResponse> getReplyList(Integer id, Integer type,
        PageParamRequest pageParamRequest);

    StoreProductReplayCountResponse getReplyCount(Integer id);

    String getPacketPriceRange(StoreProductResponse storeProductResponse, boolean isPromoter);
}
