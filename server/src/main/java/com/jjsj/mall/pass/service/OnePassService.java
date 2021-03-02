package com.jjsj.mall.pass.service;

import com.alibaba.fastjson.JSONObject;
import com.jjsj.common.MyRecord;
import com.jjsj.mall.pass.request.MealCodeRequest;
import com.jjsj.mall.pass.request.OnePassLoginRequest;
import com.jjsj.mall.pass.request.OnePassRegisterRequest;
import com.jjsj.mall.pass.request.OnePassUpdateRequest;
import com.jjsj.mall.pass.request.OnePassUserRecordRequest;
import com.jjsj.mall.pass.request.ServiceOpenRequest;
import com.jjsj.mall.pass.vo.OnePassLogisticsQueryVo;

/**
 * 一号通 OnePassService 接口
 */
public interface OnePassService {

    /**
     * 获取用户验证码
     *
     * @param phone 手机号
     */
    Object sendUserCode(String phone, Integer types);

    /**
     * 注册用户
     *
     * @param registerRequest 注册参数
     */
    String register(OnePassRegisterRequest registerRequest);

    /**
     * 用户登录
     *
     * @param request 登录参数
     */
    MyRecord login(OnePassLoginRequest request);

    /**
     * 判断是否登录
     */
    MyRecord isLogin();

    /**
     * 一号通用户信息
     */
    JSONObject info();

    /**
     * 用户注销
     */
    Boolean logOut();

    /**
     * 修改密码
     *
     * @param request 修改密码参数
     */
    Boolean updatePassword(OnePassUpdateRequest request);

    /**
     * 修改手机号
     *
     * @param request 修改手机参数
     */
    Boolean updatePhone(OnePassUpdateRequest request);

    /**
     * 套餐列表
     *
     * @param type 套餐类型：sms,短信；expr_query,物流查询；expr_dump,电子面单；copy,产品复制
     */
    JSONObject mealList(String type);

    /**
     * 套餐购买
     *
     * @param request 购买参数
     */
    JSONObject mealCode(MealCodeRequest request);

    /**
     * 服务开通
     *
     * @param request 服务开通参数
     */
    Boolean serviceOpen(ServiceOpenRequest request);

    /**
     * 用量记录
     *
     * @param request 用量记录查询参数
     */
    JSONObject userRecord(OnePassUserRecordRequest request);

    /**
     * 复制平台商品
     *
     * @param url 商品链接
     */
    JSONObject copyGoods(String url);

    /**
     * 电子面单
     */
    MyRecord expressDump(MyRecord record);

    /**
     * 物流追踪
     *
     * @param expressNo 快递单号
     * @param com 快递公司简写
     * @return OnePassLogisticsQueryVo
     */
    OnePassLogisticsQueryVo exprQuery(String expressNo, String com);

    /**
     * 修改手机号——验证账号密码
     *
     * @return Boolean
     */
    Boolean beforeUpdatePhoneValidator(OnePassLoginRequest request);
}
