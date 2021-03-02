package com.jjsj.mall.express.dao;

import com.jjsj.mall.express.model.ShippingTemplatesFree;
import com.jjsj.mall.express.request.ShippingTemplatesFreeRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 *  Mapper 接口
 
 */
public interface ShippingTemplatesFreeDao extends BaseMapper<ShippingTemplatesFree> {

    List<ShippingTemplatesFreeRequest> getListGroup(Integer tempId);
}
