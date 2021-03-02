package com.jjsj.mall.front.vo;

import lombok.Data;

/**
 * 订单计算价格
 */
@Data
public class PriceGroup {

    private Integer tempId;

    private PriceItem priceItem;
}
