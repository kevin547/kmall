package com.jjsj.mall.sms;

import lombok.Data;

/**
 * 短信发送状态同步 response 解析
 */
@Data
public class SmsResultVo {

    private Integer resultcode;
    private String phone;
    private Integer id;
    private String _resultcode;
}
