package com.jjsj.mall.store.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 核销月详情Response

 */
@Data
public class StoreStaffDetail {
    private Integer count;
    private BigDecimal price;
    private String time;
}
