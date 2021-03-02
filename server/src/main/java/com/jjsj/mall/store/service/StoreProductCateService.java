package com.jjsj.mall.store.service;

import com.jjsj.common.PageParamRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.store.model.StoreProductCate;
import com.jjsj.mall.store.request.StoreProductCateSearchRequest;

import java.util.List;

/**
 * StoreProductCateService 接口

 */
public interface StoreProductCateService extends IService<StoreProductCate> {

    List<StoreProductCate> getList(StoreProductCateSearchRequest request, PageParamRequest pageParamRequest);

    List<StoreProductCate> getByProductId(Integer productId);

//    Integer updateByProductId(StoreProductCate storeProductCate);
}
