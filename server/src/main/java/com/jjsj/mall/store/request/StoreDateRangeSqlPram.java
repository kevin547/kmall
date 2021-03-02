package com.jjsj.mall.store.request;

import lombok.Data;

/**
 * 订单时间参数，开始时间结束时间

 */
@Data
public class StoreDateRangeSqlPram {

    public StoreDateRangeSqlPram(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private String startTime;
    private String endTime;
}
