package com.jjsj.im;

import lombok.Data;

/**
 * 消息Vo对象 
 */
@Data
public class MessageVo {

    public Integer fromId;
    public Integer toId;
    public String message;

    public MessageVo(Integer fromId, Integer toId, String message) {
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
    }
}
