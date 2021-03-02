package com.jjsj.mall.store.dao;

import com.jjsj.mall.store.model.StoreOrder;
import com.jjsj.mall.store.request.StoreDateRangeSqlPram;
import com.jjsj.mall.store.request.StoreOrderStaticsticsRequest;
import com.jjsj.mall.store.response.StoreOrderStatisticsChartItemResponse;
import com.jjsj.mall.store.response.StoreStaffDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单表 Mapper 接口

 */
public interface StoreOrderDao extends BaseMapper<StoreOrder> {

    BigDecimal getTotalPrice(String where);

    BigDecimal getRefundPrice(String where);

    Integer getRefundTotal(String where);

    /**
     * 核销详情 月数据
     * @param request 分页和日期
     * @return 月数据
     */
    List<StoreStaffDetail> getOrderVerificationDetail(StoreOrderStaticsticsRequest request);

    /**
     * 订单统计详情 price
     * @param pram 时间区间参数
     * @return 月数据
     */
    List<StoreOrderStatisticsChartItemResponse> getOrderStatisticsPriceDetail(
        StoreDateRangeSqlPram pram);

    /**
     * 订单统计详情 订单量
     * @param pram 时间区间参数
     * @return 月数据
     */
    List<StoreOrderStatisticsChartItemResponse> getOrderStatisticsOrderCountDetail(StoreDateRangeSqlPram pram);

}
