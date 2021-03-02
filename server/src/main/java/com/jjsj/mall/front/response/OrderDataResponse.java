package com.jjsj.mall.front.response;

import java.math.BigDecimal;
import lombok.Data;

/**
 * H5订单头部数量
 */
@Data
public class OrderDataResponse {

    private int completeCount;
    private int evaluatedCount;
    private int verificationCount;
    private int orderCount;
    private int receivedCount;
    private int refundCount;
    private BigDecimal sumPrice;
    private int unPaidCount;
    private int unShippedCount;
}
