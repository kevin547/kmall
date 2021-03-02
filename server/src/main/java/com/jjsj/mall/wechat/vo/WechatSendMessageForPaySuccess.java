package com.jjsj.mall.wechat.vo;

import lombok.Data;

/**
 * 付款成功通知

 */
@Data
public class WechatSendMessageForPaySuccess {
    public WechatSendMessageForPaySuccess(String fuKuanDanHao, String fuKuanJinE, String fuKuanShiJian, String wenXinTiShi, String dingDanJinE, String shangPinQingDan) {
        FuKuanDanHao = fuKuanDanHao;
        FuKuanJinE = fuKuanJinE;
        FuKuanShiJian = fuKuanShiJian;
        WenXinTiShi = wenXinTiShi;
        DingDanJinE = dingDanJinE;
        ShangPinQingDan = shangPinQingDan;
    }

    private String FuKuanDanHao;
    private String FuKuanJinE;
    private String FuKuanShiJian;
    private String WenXinTiShi;
    private String DingDanJinE;
    private String ShangPinQingDan;
}
