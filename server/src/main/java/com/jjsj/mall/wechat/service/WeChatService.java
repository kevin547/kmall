package com.jjsj.mall.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.jjsj.mall.payment.vo.wechat.CreateOrderResponseVo;
import com.jjsj.mall.wechat.response.WeChatAuthorizeLoginGetOpenIdResponse;
import com.jjsj.mall.wechat.response.WeChatAuthorizeLoginUserInfoResponse;
import com.jjsj.mall.wechat.response.WeChatProgramAuthorizeLoginGetOpenIdResponse;
import com.jjsj.mall.front.response.UserRechargePaymentResponse;
import com.jjsj.mall.wechat.vo.ProgramAddMyTempVo;
import com.jjsj.mall.wechat.vo.ProgramCategoryVo;
import com.jjsj.mall.wechat.vo.ProgramTempKeywordsVo;
import com.jjsj.mall.wechat.vo.ProgramTempVo;
import com.jjsj.mall.wechat.vo.TemplateMessageVo;
import com.jjsj.mall.wechat.vo.*;

import java.util.HashMap;
import java.util.List;

/**
 * WeChatPublicService 接口

 */
public interface WeChatService {

    JSONObject get();

    JSONObject create(String data);

    JSONObject delete();

    JSONObject getSelf();

    JSONObject createSelf(String data);

    JSONObject deleteSelf(String menuId);

    void pushKfMessage(HashMap<String, Object> map);

    JSONObject createTags(String name);

    JSONObject getTagsList();

    JSONObject updateTags(String id, String name);

    JSONObject deleteTags(String id);

    JSONObject getUserListByTagsId(String id, String nextOpenId);

    JSONObject memberBatchTags(String id, String data);

    JSONObject memberBatchUnTags(String id, String data);

    JSONObject getTagsListByUserId(String openId);

    String getAuthorizeUrl();

    WeChatAuthorizeLoginGetOpenIdResponse authorizeLogin(String code);

    WeChatAuthorizeLoginUserInfoResponse getUserInfo(String openId, String token);

    Object getJsSdkConfig(String url);

    boolean sendPublicTempMessage(TemplateMessageVo templateMessage);

    boolean sendProgramTempMessage(TemplateMessageVo templateMessage);

    JSONObject getIndustry();

    String getUploadMedia();

    String getMedia();

    JSONObject getMediaInfo(String type, int offset, int count);

    int getMediaCount(String type);

    WeChatProgramAuthorizeLoginGetOpenIdResponse programAuthorizeLogin(String code);

    String qrCode(String page, String uri);

    UserRechargePaymentResponse response(CreateOrderResponseVo responseVo);

    List<ProgramCategoryVo> getProgramCategory();

    List<ProgramTempVo> getProgramPublicTempList(int page);

    List<ProgramTempKeywordsVo> getWeChatKeywordsByTid(Integer tid);

    String programAddMyTemp(ProgramAddMyTempVo programAddMyTempVo);

    void programDeleteMyTemp(String myTempId);
}