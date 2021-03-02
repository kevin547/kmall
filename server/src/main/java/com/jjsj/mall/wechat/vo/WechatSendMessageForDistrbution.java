package com.jjsj.mall.wechat.vo;

import lombok.Data;

/**
 * 配送通知

 */
@Data
public class WechatSendMessageForDistrbution {
    public WechatSendMessageForDistrbution(String dingDanBianHao, String peiSongRenYuan, String lianXiDianHua, String dingDanZhuangTai, String yuJiSongDaShiJian, String shangPinMingCheng, String peiSongShiJian) {
        DingDanBianHao = dingDanBianHao;
        PeiSongRenYuan = peiSongRenYuan;
        LianXiDianHua = lianXiDianHua;
        DingDanZhuangTai = dingDanZhuangTai;
        YuJiSongDaShiJian = yuJiSongDaShiJian;
        ShangPinMingCheng = shangPinMingCheng;
        PeiSongShiJian = peiSongShiJian;
    }

    private String DingDanBianHao;
    private String PeiSongRenYuan;
    private String LianXiDianHua;
    private String DingDanZhuangTai;
    private String YuJiSongDaShiJian;
    private String ShangPinMingCheng;
    private String PeiSongShiJian;
}
