package com.jjsj.mall.wechat.service;

import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.wechat.model.WechatReply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.wechat.request.WechatReplySearchRequest;

import java.util.List;

/**
 *  WechatReplyService 接口

 */
public interface WechatReplyService extends IService<WechatReply> {

    List<WechatReply> getList(WechatReplySearchRequest request, PageParamRequest pageParamRequest);

    Boolean create(WechatReply wechatReply);

    Boolean updateVo(WechatReply wechatReply);

    WechatReply getVoByKeywords(String keywords);

    WechatReply getInfoException(Integer id, boolean isTrue);

    WechatReply getInfo(Integer id);
}