package com.jjsj.mall.system.service.impl;

import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageHelper;

import com.jjsj.mall.system.model.SystemGroup;
import com.jjsj.mall.system.dao.SystemGroupDao;
import com.jjsj.mall.system.request.SystemGroupSearchRequest;
import com.jjsj.mall.system.service.SystemGroupService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * SystemGroupServiceImpl 接口实现

 */
@Service
public class SystemGroupServiceImpl extends ServiceImpl<SystemGroupDao, SystemGroup> implements SystemGroupService {

    @Resource
    private SystemGroupDao dao;


    /**
    * 列表
    * @param request 请求参数
    * @param pageParamRequest 分页类参数
    *  @author kepler
    * @since 2020-05-15
    * @return List<SystemGroup>
    */
    @Override
    public List<SystemGroup> getList(SystemGroupSearchRequest request, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());

        //带 SystemGroup 类的多条件查询
        LambdaQueryWrapper<SystemGroup> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isBlank(request.getKeywords())){
            lambdaQueryWrapper.like(SystemGroup::getName, request.getKeywords());
        }
        lambdaQueryWrapper.orderByDesc(SystemGroup::getId);
        return dao.selectList(lambdaQueryWrapper);
    }

}

