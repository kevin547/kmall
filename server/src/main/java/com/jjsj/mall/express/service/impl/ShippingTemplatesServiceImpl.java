package com.jjsj.mall.express.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jjsj.common.PageParamRequest;
import com.jjsj.exception.MallException;
import com.github.pagehelper.PageHelper;

import com.jjsj.mall.express.model.ShippingTemplates;
import com.jjsj.mall.express.dao.ShippingTemplatesDao;
import com.jjsj.mall.express.request.ShippingTemplatesFreeRequest;
import com.jjsj.mall.express.request.ShippingTemplatesRegionRequest;
import com.jjsj.mall.express.request.ShippingTemplatesRequest;
import com.jjsj.mall.express.request.ShippingTemplatesSearchRequest;
import com.jjsj.mall.express.service.ShippingTemplatesFreeService;
import com.jjsj.mall.express.service.ShippingTemplatesRegionService;
import com.jjsj.mall.express.service.ShippingTemplatesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* ShippingTemplatesServiceImpl 接口实现

*/
@Service
public class ShippingTemplatesServiceImpl extends ServiceImpl<ShippingTemplatesDao, ShippingTemplates> implements ShippingTemplatesService {

    @Resource
    private ShippingTemplatesDao dao;

    @Autowired
    private ShippingTemplatesRegionService shippingTemplatesRegionService;

    @Autowired
    private ShippingTemplatesFreeService shippingTemplatesFreeService;

    /**
    * 列表
    * @param request 请求参数
    * @param pageParamRequest 分页类参数
    *  @author kepler
    * @since 2020-04-17
    * @return List<ShippingTemplates>
    */
    @Override
    public List<ShippingTemplates> getList(ShippingTemplatesSearchRequest request, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<ShippingTemplates> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isBlank(request.getKeywords())){
            lambdaQueryWrapper.like(ShippingTemplates::getName, request.getKeywords());
        }
        lambdaQueryWrapper.orderByDesc(ShippingTemplates::getSort).orderByDesc(ShippingTemplates::getId);
        return dao.selectList(lambdaQueryWrapper);
    }

    /**
     * 检测运费模板是否存在
     * @param tempId Integer 模板id
     *  @author kepler
     * @since 2020-05-07
     */
    @Override
    public void checkExpressTemp(Integer tempId) {
        if(getById(tempId) == null){
            throw new MallException("没有相关运费模板");
        }
    }

    /**
     * 新增
     * @param request 新增参数
     *  @author kepler
     * @since 2020-04-17
     * @return bool
     */
    @Override
    public boolean create(ShippingTemplatesRequest request) {
        ShippingTemplates shippingTemplates = new ShippingTemplates();
        shippingTemplates.setName(request.getName());
        shippingTemplates.setSort(request.getSort());
        shippingTemplates.setType(request.getType());
        shippingTemplates.setAppoint(request.getAppoint());

        save(shippingTemplates);

        //区域运费
        List<ShippingTemplatesRegionRequest> shippingTemplatesRegionRequestList = request.getShippingTemplatesRegionRequestList();

        if(shippingTemplatesRegionRequestList.size() > 0){
            shippingTemplatesRegionService.saveAll(shippingTemplatesRegionRequestList, request.getType(), shippingTemplates.getId());
        }


        List<ShippingTemplatesFreeRequest> shippingTemplatesFreeRequestList = request.getShippingTemplatesFreeRequestList();
        if(null != shippingTemplatesFreeRequestList && shippingTemplatesFreeRequestList.size() > 0 && request.getAppoint()){
            shippingTemplatesFreeService.saveAll(shippingTemplatesFreeRequestList, request.getType(), shippingTemplates.getId());
        }

        return true;
    }

    /**
     * 新增
     * @param id Integer 模板id
     * @param request ShippingTemplatesRequest 新增参数
     *  @author kepler
     * @since 2020-04-17
     * @return bool
     */
    @Override
    public boolean update(Integer id, ShippingTemplatesRequest request) {
        ShippingTemplates shippingTemplates = new ShippingTemplates();
        shippingTemplates.setId(id);
        shippingTemplates.setName(request.getName());
        shippingTemplates.setSort(request.getSort());
        shippingTemplates.setType(request.getType());
        shippingTemplates.setAppoint(request.getAppoint());


        updateById(shippingTemplates);

        //区域运费
        List<ShippingTemplatesRegionRequest> shippingTemplatesRegionRequestList = request.getShippingTemplatesRegionRequestList();

        if(shippingTemplatesRegionRequestList.size() < 1){
            throw new MallException("请设置区域配送信息！");
        }
        shippingTemplatesRegionService.saveAll(shippingTemplatesRegionRequestList, request.getType(), shippingTemplates.getId());

        List<ShippingTemplatesFreeRequest> shippingTemplatesFreeRequestList = request.getShippingTemplatesFreeRequestList();
        if(CollUtil.isNotEmpty(shippingTemplatesFreeRequestList) && request.getAppoint()){
            shippingTemplatesFreeService.saveAll(shippingTemplatesFreeRequestList, request.getType(), shippingTemplates.getId());
        }

        return true;
    }

    /**
     * 删除
     * @param id Integer
     *  @author kepler
     * @since 2020-04-17
     * @return boolean
     */
    @Override
    public boolean remove(Integer id) {
        shippingTemplatesRegionService.delete(id);
        shippingTemplatesFreeService.delete(id);
        return removeById(id);
    }

    /**
     * 根据id集合获取
     * @param ids 模版ids
     * @return 模版集合
     */
    @Override
    public List<ShippingTemplates> getListInIds(List<Integer> ids) {
        LambdaQueryWrapper<ShippingTemplates> lqw = new LambdaQueryWrapper<>();
        lqw.in(ShippingTemplates::getId, ids);
        return dao.selectList(lqw);
    }
}

