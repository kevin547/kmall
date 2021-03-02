package com.jjsj.mall.system.service;

import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.system.model.SystemFormTemp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.system.request.SystemFormCheckRequest;
import com.jjsj.mall.system.request.SystemFormTempSearchRequest;

import java.util.List;

/**
 * SystemFormTempService 接口

 */
public interface SystemFormTempService extends IService<SystemFormTemp> {

    List<SystemFormTemp> getList(SystemFormTempSearchRequest request, PageParamRequest pageParamRequest);

    void checkForm(SystemFormCheckRequest systemFormCheckRequest);
}