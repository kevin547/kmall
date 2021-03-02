package com.jjsj.mall.user.response;

import lombok.Data;

/**
 * 资金操作类型 
 */
@Data
public class BillType {

    public BillType(String title, String type) {
        this.title = title;
        this.type = type;
    }

    private String title;
    private String type;
}
