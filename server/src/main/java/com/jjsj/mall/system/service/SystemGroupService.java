package com.jjsj.mall.system.service;

import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.system.model.SystemGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.system.request.SystemGroupSearchRequest;

import java.util.List;

/**
 * SystemGroupService 接口

 */
public interface SystemGroupService extends IService<SystemGroup> {

    List<SystemGroup> getList(SystemGroupSearchRequest request, PageParamRequest pageParamRequest);
}
