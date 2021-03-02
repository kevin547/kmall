package com.jjsj.mall.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jjsj.mall.finance.model.UserFundsMonitor;

import java.util.HashMap;
import java.util.List;

/**
 * 用户充值表 Mapper 接口
 
 */
public interface UserFundsMonitorDao extends BaseMapper<UserFundsMonitor> {

    /**
     * 佣金列表
     *  @author kepler
     * @since 2020-04-28
     * @return List<User>
     */
    List<UserFundsMonitor> getFundsMonitor(HashMap<String, Object> map);
}
