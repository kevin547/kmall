package com.jjsj.mall.wechat.service;

import com.jjsj.common.PageParamRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.wechat.model.TemplateMessage;
import com.jjsj.mall.wechat.request.TemplateMessageSearchRequest;
import com.jjsj.mall.wechat.vo.TemplateMessageIndustryVo;

import java.util.HashMap;
import java.util.List;

/**
 *  TemplateMessageService 接口

 */
public interface TemplateMessageService extends IService<TemplateMessage> {

    List<TemplateMessage> getList(TemplateMessageSearchRequest request, PageParamRequest pageParamRequest);

    void push(String tempKey, HashMap<String, String> map, Integer userId, String type);

    TemplateMessage infoException(Integer id);

    void consumePublic();

    void consumeProgram();

    TemplateMessageIndustryVo getIndustry();

    /**
     * 发送公众号模板消息
     * @param templateNo 模板消息编号
     * @param temMap 内容Map
     * @param openId 微信用户openId
     */
    void pushTemplateMessage(String templateNo, HashMap<String, String> temMap, String openId);

    /**
     * 发送小程序订阅消息
     * @param templateNo 模板消息编号
     * @param temMap 内容Map
     * @param openId 微信用户openId
     */
    void pushMiniTemplateMessage(String templateNo, HashMap<String, String> temMap, String openId);

    /**
     * 获取小程序订阅模板编号
     * @param type 场景类型(支付之前：beforePay|支付成功：afterPay|申请退款：refundApply|充值之前：beforeRecharge|创建砍价：createBargain|参与拼团：pink|取消拼团：cancelPink)
     * @return
     */
    List<TemplateMessage> getMiniTempList(String type);
}