package com.jjsj.mall.wechat.vo;

import lombok.Data;

/**
 * 发货通知

 */
@Data
public class WechatSendMessageForPackage {
    public WechatSendMessageForPackage(String peiSongFangShi, String dingDanHao, String shouHuoDiZhi, String keHuDianHua, String shouHuoRen) {
        PeiSongFangShi = peiSongFangShi;
        DingDanHao = dingDanHao;
        ShouHuoDiZhi = shouHuoDiZhi;
        KeHuDianHua = keHuDianHua;
        ShouHuoRen = shouHuoRen;
    }

    private String PeiSongFangShi;
    private String DingDanHao;
    private String ShouHuoDiZhi;
    private String KeHuDianHua;
    private String ShouHuoRen;
}
