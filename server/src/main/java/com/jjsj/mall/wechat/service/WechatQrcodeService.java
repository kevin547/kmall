package com.jjsj.mall.wechat.service;

import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.wechat.model.WechatQrcode;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.wechat.request.WechatQrcodeSearchRequest;

import java.util.List;

/**
 *  WechatQrcodeService 接口

 */
public interface WechatQrcodeService extends IService<WechatQrcode> {

    List<WechatQrcode> getList(WechatQrcodeSearchRequest request, PageParamRequest pageParamRequest);
}