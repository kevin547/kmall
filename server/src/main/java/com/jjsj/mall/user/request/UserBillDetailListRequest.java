package com.jjsj.mall.user.request;

import lombok.Data;

/**
 * 用户操作记录查询
 */
@Data
public class UserBillDetailListRequest {

    private String nickname; // 订单号或者推荐人昵称=备注模糊查询
    private String dateLimit; // 时间参数
    private String keywords;
}
