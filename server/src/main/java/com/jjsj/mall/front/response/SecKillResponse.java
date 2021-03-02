package com.jjsj.mall.front.response;

import lombok.Data;

/**
 * 秒杀Header 
 */
@Data
public class SecKillResponse {

    public SecKillResponse() {
    }

    public SecKillResponse(Integer id, String slide, String statusName, String time, int status,
        String timeSwap) {
        this.id = id;
        this.slide = slide;
        this.statusName = statusName;
        this.time = time;
        this.status = status;
        this.timeSwap = timeSwap;
    }

    private Integer id;
    private String slide;
    private String statusName; // 已结束 抢购中 即将开始
    private int status;
    private String time;
    private String timeSwap;
}
