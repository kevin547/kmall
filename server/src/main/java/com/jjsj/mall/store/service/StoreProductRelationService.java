package com.jjsj.mall.store.service;

import com.jjsj.common.PageParamRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.front.request.UserCollectAllRequest;
import com.jjsj.mall.front.request.UserCollectRequest;
import com.jjsj.mall.store.model.StoreProduct;
import com.jjsj.mall.store.model.StoreProductRelation;
import com.jjsj.mall.store.request.StoreProductRelationSearchRequest;

import java.util.HashMap;
import java.util.List;

/**
 * StoreProductRelationService 接口

 */
public interface StoreProductRelationService extends IService<StoreProductRelation> {

    List<StoreProduct> getList(StoreProductRelationSearchRequest request, PageParamRequest pageParamRequest);

    HashMap<Integer, Integer> getLikeCountListInProductId(List<Integer> idList);

    HashMap<Integer, Integer> getCollectCountListInProductId(List<Integer> idList);

    List<StoreProductRelation> getList(Integer productId, String type);

    boolean delete(UserCollectRequest request);

    boolean all(UserCollectAllRequest request);

    List<StoreProductRelation> getLikeOrCollectByUser(Integer userId, Integer productId,boolean isLike);
}
