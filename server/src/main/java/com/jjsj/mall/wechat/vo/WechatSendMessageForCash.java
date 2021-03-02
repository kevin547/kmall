package com.jjsj.mall.wechat.vo;

import lombok.Data;

/**
 * 提现结果通知

 */
@Data
public class WechatSendMessageForCash {
    public WechatSendMessageForCash(String tiXianZhuangTai, String tiXianJinE, String tiXianZhangHao, String tiXianShiJian, String tiXianShuoMing, String xingMing, String shouXuFei, String daKuanFangShi, String daKuanYuanYin, String tiXianDanHao, String tiXianFangShi, String shiBaiYuanYin, String huiYuanMingCheng) {
        TiXianZhuangTai = tiXianZhuangTai;
        TiXianJinE = tiXianJinE;
        TiXianZhangHao = tiXianZhangHao;
        TiXianShiJian = tiXianShiJian;
        TiXianShuoMing = tiXianShuoMing;
        XingMing = xingMing;
        ShouXuFei = shouXuFei;
        DaKuanFangShi = daKuanFangShi;
        DaKuanYuanYin = daKuanYuanYin;
        TiXianDanHao = tiXianDanHao;
        TiXianFangShi = tiXianFangShi;
        ShiBaiYuanYin = shiBaiYuanYin;
        HuiYuanMingCheng = huiYuanMingCheng;
    }

    private String TiXianZhuangTai;
    private String TiXianJinE;
    private String TiXianZhangHao;
    private String TiXianShiJian;
    private String TiXianShuoMing;
    private String XingMing;
    private String ShouXuFei;
    private String DaKuanFangShi;
    private String DaKuanYuanYin;
    private String TiXianDanHao;
    private String TiXianFangShi;
    private String ShiBaiYuanYin;
    private String HuiYuanMingCheng;
}
