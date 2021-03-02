package com.jjsj.mall.store.service;

import com.jjsj.common.PageParamRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.store.model.StoreProductAttr;
import com.jjsj.mall.store.request.StoreProductAttrSearchRequest;

import java.util.List;

/**
 * StoreProductAttrService 接口

 */
public interface StoreProductAttrService extends IService<StoreProductAttr> {

    List<StoreProductAttr> getList(StoreProductAttrSearchRequest request, PageParamRequest pageParamRequest);

    Boolean batchSave(List<StoreProductAttr> storeProductAttrs);

    Boolean batchUpdate(List<StoreProductAttr> storeProductAttrs);

    /**
     * 根据基本属性查询商品属性详情
     * @param storeProductAttr 商品属性
     * @return 查询商品属性集合
     */
    List<StoreProductAttr> getByEntity(StoreProductAttr storeProductAttr);

    /**
     * 根据id查询商品属性详情
     * @param productId 商品id
     * @return 查询结果
     */
    List<StoreProductAttr> getByProductId(int productId);

    /**
     * 根据id删除商品
     * @param productId 待删除商品id
     * @param type 类型区分是是否添加营销
     */
    void removeByProductId(Integer productId,int type);

}
