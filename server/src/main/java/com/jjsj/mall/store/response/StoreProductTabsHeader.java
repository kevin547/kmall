package com.jjsj.mall.store.response;

import lombok.Data;

/**
 * StoreProductTabsHeader

 */
@Data
public class StoreProductTabsHeader {
    private Integer count;
    private String name;
    private Integer type; // 1=出售中商品 2=仓库中商品 4=已经售罄商品 5=警戒库存 6=商品回收站

    public StoreProductTabsHeader() {
    }

    public StoreProductTabsHeader(Integer count, String name, Integer type) {
        this.count = count;
        this.name = name;
        this.type = type;
    }
}
