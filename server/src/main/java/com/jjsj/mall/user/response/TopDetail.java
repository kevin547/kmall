package com.jjsj.mall.user.response;

import com.jjsj.mall.user.model.User;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 会员详情顶部信息 
 */
@Data
public class TopDetail {

    private User user;
    // 余额
    private BigDecimal balance;
    // 积分
    private Integer integralCount;
    // 总计订单
    private Integer allOrderCount;
    // 本月订单
    private Integer mothOrderCount;
    // 总消费金额
    private BigDecimal allConsumeCount;
    // 本月消费金额
    private BigDecimal mothConsumeCount;
}
