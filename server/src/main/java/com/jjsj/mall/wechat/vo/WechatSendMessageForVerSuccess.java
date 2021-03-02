package com.jjsj.mall.wechat.vo;

import lombok.Data;

/**
 * 核销成功通知

 */
@Data
public class WechatSendMessageForVerSuccess {
    public WechatSendMessageForVerSuccess(String huoDongMingCheng, String shangPinMingCheng, String dingDanHao, String heXiaoShiJian, String heXiaoZongE, String beiZhu, String menDian) {
        HuoDongMingCheng = huoDongMingCheng;
        ShangPinMingCheng = shangPinMingCheng;
        DingDanHao = dingDanHao;
        HeXiaoShiJian = heXiaoShiJian;
        HeXiaoZongE = heXiaoZongE;
        BeiZhu = beiZhu;
        MenDian = menDian;
    }

    private String HuoDongMingCheng;
    private String ShangPinMingCheng;
    private String DingDanHao;
    private String HeXiaoShiJian;
    private String HeXiaoZongE;
    private String BeiZhu;
    private String MenDian;
}
