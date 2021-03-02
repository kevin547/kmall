package com.jjsj.mall.finance.dao;

import com.jjsj.mall.finance.model.UserRecharge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;

/**
 * 用户充值表 Mapper 接口
 
 */
public interface UserRechargeDao extends BaseMapper<UserRecharge> {

    /**
     * 根据类型获取该类型充值总金额
     * @param type  充值类型
     * @return      该类型充值总金额
     */
    BigDecimal getSumByType(String type);

    /**
     * 获取退款总金额
     * @return 退款总金额
     */
    BigDecimal getSumByRefund();
}
