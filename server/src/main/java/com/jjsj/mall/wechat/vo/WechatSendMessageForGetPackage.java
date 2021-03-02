package com.jjsj.mall.wechat.vo;

import lombok.Data;

/**
 * 收货通知

 */
@Data
public class WechatSendMessageForGetPackage {
    public WechatSendMessageForGetPackage(String dingDanLeiXing, String dingDanShangPin, String maiJiaXinXi, String shouHuoDiZhi, String shouHuoShiJian, String dingDanBianHao) {
        DingDanLeiXing = dingDanLeiXing;
        DingDanShangPin = dingDanShangPin;
        MaiJiaXinXi = maiJiaXinXi;
        ShouHuoDiZhi = shouHuoDiZhi;
        ShouHuoShiJian = shouHuoShiJian;
        DingDanBianHao = dingDanBianHao;
    }

    private String DingDanLeiXing;
    private String DingDanShangPin;
    private String MaiJiaXinXi;
    private String ShouHuoDiZhi;
    private String ShouHuoShiJian;
    private String DingDanBianHao;
}
