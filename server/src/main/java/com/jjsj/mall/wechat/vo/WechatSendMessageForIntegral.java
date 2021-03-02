package com.jjsj.mall.wechat.vo;

import lombok.Data;

/**
 * 赠送积分消息通知

 */
@Data
public class WechatSendMessageForIntegral {

    public WechatSendMessageForIntegral(String shuoMing, String dingDanBianHao, String shangPinMingCheng, String zhiFuJinE, String huoDeJiFen, String leiJiJiFen, String jiaoYiShiJian, String beiZhu, String menDian, String daoZhangYuanYin) {
        ShuoMing = shuoMing;
        DingDanBianHao = dingDanBianHao;
        ShangPinMingCheng = shangPinMingCheng;
        ZhiFuJinE = zhiFuJinE;
        HuoDeJiFen = huoDeJiFen;
        LeiJiJiFen = leiJiJiFen;
        JiaoYiShiJian = jiaoYiShiJian;
        BeiZhu = beiZhu;
        MenDian = menDian;
        DaoZhangYuanYin = daoZhangYuanYin;
    }

    private String ShuoMing;
    private String DingDanBianHao;
    private String ShangPinMingCheng;
    private String ZhiFuJinE;
    private String HuoDeJiFen;
    private String LeiJiJiFen;
    private String JiaoYiShiJian;
    private String BeiZhu;
    private String MenDian;
    private String DaoZhangYuanYin;
}
