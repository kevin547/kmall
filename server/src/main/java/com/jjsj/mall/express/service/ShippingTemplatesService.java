package com.jjsj.mall.express.service;

import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.express.model.ShippingTemplates;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.express.request.ShippingTemplatesRequest;
import com.jjsj.mall.express.request.ShippingTemplatesSearchRequest;

import java.util.List;

/**
* ShippingTemplatesService 接口

*/
public interface ShippingTemplatesService extends IService<ShippingTemplates> {

    List<ShippingTemplates> getList(ShippingTemplatesSearchRequest request, PageParamRequest pageParamRequest);

    void checkExpressTemp(Integer tempId);

    boolean create(ShippingTemplatesRequest request);

    boolean update(Integer id, ShippingTemplatesRequest request);

    boolean remove(Integer id);

    /**
     * 根据模版id集合获取订单集合
     * @param ids 模版ids
     * @return 模版集合
     */
    List<ShippingTemplates> getListInIds(List<Integer> ids);
}
