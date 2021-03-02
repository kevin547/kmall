package com.jjsj.mall.express.service;

import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.express.model.ShippingTemplatesRegion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.express.request.ShippingTemplatesRegionRequest;

import java.util.List;

/**
* ShippingTemplatesRegionService 接口

*/
public interface ShippingTemplatesRegionService extends IService<ShippingTemplatesRegion> {

    List<ShippingTemplatesRegion> getList(PageParamRequest pageParamRequest);

    /**
     * 根据ids和cityid查询
     * @param ids id集合
     * @param cityId 城市id
     * @return 运费模版集合
     */
    List<ShippingTemplatesRegion> getListInIdsAndCityId(List<Integer> ids,Integer cityId);

    void saveAll(List<ShippingTemplatesRegionRequest> shippingTemplatesRegionRequestList, Integer type, Integer id);

    List<ShippingTemplatesRegionRequest> getListGroup(Integer tempId);

    void delete(Integer tempId);
}
