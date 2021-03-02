package com.jjsj.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.system.model.SystemCity;
import com.jjsj.mall.system.request.SystemCityRequest;
import com.jjsj.mall.system.request.SystemCitySearchRequest;

import java.util.List;

/**
 * SystemCityService 接口

 */
public interface SystemCityService extends IService<SystemCity> {

    Object getList(SystemCitySearchRequest request);

    boolean updateStatus(Integer id, Boolean status);

    boolean update(Integer id, SystemCityRequest request);

    Object getListTree();

    String getStringNameInId(String cityIdList);

    List<Integer> getCityIdList();

    SystemCity getCityByCityId(Integer cityId);
}