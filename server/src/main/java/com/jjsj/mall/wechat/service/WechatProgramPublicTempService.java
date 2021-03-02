package com.jjsj.mall.wechat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.wechat.model.WechatProgramPublicTemp;
import com.jjsj.mall.wechat.request.WechatProgramPublicTempSearchRequest;

import java.util.List;

/**
 *  WechatProgramPublicTempService 接口

 */
public interface WechatProgramPublicTempService extends IService<WechatProgramPublicTemp> {

    List<WechatProgramPublicTemp> getList(WechatProgramPublicTempSearchRequest request, PageParamRequest pageParamRequest);

    void async();
}