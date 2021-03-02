package com.jjsj.mall.wechat.vo;

import lombok.Data;

/**
 * 退款申请通知

 */
@Data
public class WechatSendMessageForReFundNotify {
    public WechatSendMessageForReFundNotify(String dingDanShangPin, String dingDanJinE, String xiaDanShiJian, String dingDanBianHao, String shenQingShiJian, String shangJiaMingCheng, String tuiKuanZhuangTai, String beiZhu, String tuiKuanJinE, String tuiKuanYuanYin, String yongHuDianHua, String sheBeiMingCheng) {
        DingDanShangPin = dingDanShangPin;
        DingDanJinE = dingDanJinE;
        XiaDanShiJian = xiaDanShiJian;
        DingDanBianHao = dingDanBianHao;
        ShenQingShiJian = shenQingShiJian;
        ShangJiaMingCheng = shangJiaMingCheng;
        TuiKuanZhuangTai = tuiKuanZhuangTai;
        BeiZhu = beiZhu;
        TuiKuanJinE = tuiKuanJinE;
        TuiKuanYuanYin = tuiKuanYuanYin;
        YongHuDianHua = yongHuDianHua;
        SheBeiMingCheng = sheBeiMingCheng;
    }

    private String DingDanShangPin;
    private String DingDanJinE;
    private String XiaDanShiJian;
    private String DingDanBianHao;
    private String ShenQingShiJian;
    private String ShangJiaMingCheng;
    private String TuiKuanZhuangTai;
    private String BeiZhu;
    private String TuiKuanJinE;
    private String TuiKuanYuanYin;
    private String YongHuDianHua;
    private String SheBeiMingCheng;
}
