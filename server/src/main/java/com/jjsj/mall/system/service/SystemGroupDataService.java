package com.jjsj.mall.system.service;

import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.system.model.SystemGroupData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.system.request.SystemGroupDataRequest;
import com.jjsj.mall.system.request.SystemGroupDataSearchRequest;

import java.util.HashMap;
import java.util.List;

/**
 * SystemGroupDataService 接口

 */
public interface SystemGroupDataService extends IService<SystemGroupData> {

    List<SystemGroupData> getList(SystemGroupDataSearchRequest request, PageParamRequest pageParamRequest);

    boolean create(SystemGroupDataRequest systemGroupDataRequest);

    boolean update(Integer id, SystemGroupDataRequest request);

    <T> List<T> getListByGid(Integer gid, Class<T> cls);

    List<HashMap<String, Object>> getListMapByGid(Integer gid);

    <T> T getNormalInfo(Integer groupDataId, Class<T> cls);
}