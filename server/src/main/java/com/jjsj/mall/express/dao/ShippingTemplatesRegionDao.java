package com.jjsj.mall.express.dao;

import com.jjsj.mall.express.model.ShippingTemplatesRegion;
import com.jjsj.mall.express.request.ShippingTemplatesRegionRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 *  Mapper 接口
 
 */
public interface ShippingTemplatesRegionDao extends BaseMapper<ShippingTemplatesRegion> {

    List<ShippingTemplatesRegionRequest> getListGroup(Integer tempId);
}
