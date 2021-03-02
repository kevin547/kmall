package com.jjsj.mall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.front.request.UserSignInfoRequest;
import com.jjsj.mall.front.response.UserSignInfoResponse;
import com.jjsj.mall.system.vo.SystemGroupDataSignConfigVo;
import com.jjsj.mall.user.model.UserSign;
import com.jjsj.mall.user.vo.UserSignMonthVo;
import com.jjsj.mall.user.vo.UserSignVo;
import java.util.HashMap;
import java.util.List;

/**
 * UserSignService 接口实现
 */
public interface UserSignService extends IService<UserSign> {

    List<UserSignVo> getList(PageParamRequest pageParamRequest);

    List<UserSign> getListByCondition(UserSign sign, PageParamRequest pageParamRequest);

    SystemGroupDataSignConfigVo sign();

    HashMap<String, Object> get();

    List<SystemGroupDataSignConfigVo> config();

    List<UserSignMonthVo> getListGroupMonth(PageParamRequest pageParamRequest);

    UserSignInfoResponse getUserInfo(UserSignInfoRequest request);
}