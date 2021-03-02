package com.jjsj.mall.wechat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.wechat.model.WechatProgramMyTemp;
import com.jjsj.mall.wechat.request.WechatProgramMyTempRequest;
import com.jjsj.mall.wechat.request.WechatProgramMyTempSearchRequest;

import java.util.HashMap;
import java.util.List;

/**
 *  WechatProgramMyTempService 接口

 */
public interface WechatProgramMyTempService extends IService<WechatProgramMyTemp> {

    List<WechatProgramMyTemp> getList(WechatProgramMyTempSearchRequest request, PageParamRequest pageParamRequest);

    WechatProgramMyTemp info(Integer id);

    boolean updateStatus(Integer id, boolean status);

    boolean create(WechatProgramMyTempRequest wechatProgramMyTempRequest);

    boolean update(Integer id, WechatProgramMyTempRequest wechatProgramMyTempRequest);

    void push(int myTempId, HashMap<String, String> map, Integer userId);

    void async(WechatProgramMyTemp wechatProgramMyTemp);

    List<WechatProgramMyTemp> getAll();

    boolean updateType(Integer id, String type);
}