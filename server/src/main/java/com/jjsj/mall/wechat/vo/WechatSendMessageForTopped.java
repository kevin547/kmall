package com.jjsj.mall.wechat.vo;

import lombok.Data;

/**
 * 充值成功

 */
@Data
public class WechatSendMessageForTopped {
    public WechatSendMessageForTopped(String jiaoYiDanHao, String chongZhiKaHao, String chongZhiJinE, String zhangHuYuE, String chongZhiShiJian, String zengSongJinE, String beiZhu, String shiFuJinE, String shangHuMingCheng, String zhiFuFangShi) {
        JiaoYiDanHao = jiaoYiDanHao;
        ChongZhiKaHao = chongZhiKaHao;
        ChongZhiJinE = chongZhiJinE;
        ZhangHuYuE = zhangHuYuE;
        ChongZhiShiJian = chongZhiShiJian;
        ZengSongJinE = zengSongJinE;
        BeiZhu = beiZhu;
        ShiFuJinE = shiFuJinE;
        ShangHuMingCheng = shangHuMingCheng;
        ZhiFuFangShi = zhiFuFangShi;
    }

    private String JiaoYiDanHao;
    private String ChongZhiKaHao;
    private String ChongZhiJinE;
    private String ZhangHuYuE;
    private String ChongZhiShiJian;
    private String ZengSongJinE;
    private String BeiZhu;
    private String ShiFuJinE;
    private String ShangHuMingCheng;
    private String ZhiFuFangShi;
}
