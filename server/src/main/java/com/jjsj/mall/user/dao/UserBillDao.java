package com.jjsj.mall.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jjsj.mall.user.model.UserBill;
import com.jjsj.mall.user.response.UserBillResponse;
import java.util.List;
import java.util.Map;

/**
 * 用户账单表 Mapper 接口 
 */
public interface UserBillDao extends BaseMapper<UserBill> {

    List<UserBillResponse> getListAdmin(Map<String, Object> map);

    List<UserBillResponse> getListAdminAndIntegeal(Map<String, Object> map);

    List<UserBillResponse> fundMonitoring(Map<String, Object> map);
}
