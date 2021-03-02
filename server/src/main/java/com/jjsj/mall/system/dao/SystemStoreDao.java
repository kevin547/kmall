package com.jjsj.mall.system.dao;

import com.jjsj.mall.system.model.SystemStore;
import com.jjsj.mall.front.request.StoreNearRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jjsj.mall.system.vo.SystemStoreNearVo;

import java.util.List;

/**
 * 门店自提 Mapper 接口

 */
public interface SystemStoreDao extends BaseMapper<SystemStore> {

    List<SystemStoreNearVo> getNearList(StoreNearRequest request);
}

