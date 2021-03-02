package com.jjsj.mall.store.service;

import com.jjsj.common.PageParamRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.store.model.StoreProductRule;
import com.jjsj.mall.store.request.StoreProductRuleRequest;
import com.jjsj.mall.store.request.StoreProductRuleSearchRequest;

import java.util.List;

/**
 * StoreProductRuleService 接口

 */
public interface StoreProductRuleService extends IService<StoreProductRule> {

    List<StoreProductRule> getList(StoreProductRuleSearchRequest request, PageParamRequest pageParamRequest);

    /**
     * 新增商品规格
     * @param storeProductRuleRequest 规格参数
     * @return 新增结果
     */
    boolean save(StoreProductRuleRequest storeProductRuleRequest);
}
