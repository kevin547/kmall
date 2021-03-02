package com.jjsj.utils.vo;

import lombok.Data;

/**
 * 时间Vo类

 */
@Data
public class dateLimitUtilVo {
    public dateLimitUtilVo() {}
    public dateLimitUtilVo(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private String startTime; //开始时间

    private String endTime; //结束时间
}
