package com.jjsj.mall.wechatMessage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 微信消息文本模板

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MessageTextVo对象", description="微信消息文本模板")
public class MessageTextVo extends BaseMessageVo {
    public MessageTextVo() {}
    public MessageTextVo(String toUserName, String fromUserName, String content) {
        super();
        ToUserName = toUserName;
        FromUserName = fromUserName;
        Content = content;
    }

    @ApiModelProperty(value = "文本消息内容")
    private String Content;
}
