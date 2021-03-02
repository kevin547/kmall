package com.jjsj.mall.front.vo;

import com.jjsj.mall.store.model.StoreOrder;
import com.jjsj.mall.store.vo.StoreOrderInfoVo;
import java.util.List;
import lombok.Data;

/**
 * 再次下单VO对象 
 */
@Data
public class OrderAgainVo {

    private StoreOrder storeOrder;
    private List<StoreOrderInfoVo> cartInfo;
    private OrderAgainItemVo status;
    private String payTime;
    private String addTime;
    private String statusPic;
    private Integer offlinePayStatus;
}
