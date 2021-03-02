package com.jjsj.mall.statistics.service;

import com.jjsj.mall.statistics.response.HomeRateResponse;

import java.util.Map;

/**
 * 首页统计

 */
public interface HomeService{
    HomeRateResponse sales();

    HomeRateResponse user();

    HomeRateResponse views();

    HomeRateResponse order();

    Map<Object, Object> chartUser();

    Map<String, Object> chartOrder();

    Map<String, Integer> chartUserBuy();

    Map<String, Object> chartOrderInWeek();

    Map<String, Object> chartOrderInMonth();

    Map<String, Object> chartOrderInYear();
}
