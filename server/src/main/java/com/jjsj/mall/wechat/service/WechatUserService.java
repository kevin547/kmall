package com.jjsj.mall.wechat.service;

/**
 * 微信用户表 服务类

 */
public interface WechatUserService{
    void push(String userId, Integer newsId);
}
