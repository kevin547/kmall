package com.jjsj.mall.store.service;

import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.store.model.StoreOrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.store.request.StoreOrderInfoSearchRequest;
import com.jjsj.mall.store.vo.StoreOrderInfoVo;

import java.util.HashMap;
import java.util.List;

/**
 * StoreOrderInfoService 接口

 */
public interface StoreOrderInfoService extends IService<StoreOrderInfo> {

    List<StoreOrderInfo> getList(StoreOrderInfoSearchRequest request, PageParamRequest pageParamRequest);

    HashMap<Integer, List<StoreOrderInfoVo>> getMapInId(List<Integer> orderIdList);

    List<StoreOrderInfoVo> getOrderListByOrderId(Integer orderId);

    /**
     * 批量添加订单详情
     * @param storeOrderInfos 订单详情集合
     * @return 保存结果
     */
    boolean saveOrderInfos(List<StoreOrderInfo> storeOrderInfos);
}
