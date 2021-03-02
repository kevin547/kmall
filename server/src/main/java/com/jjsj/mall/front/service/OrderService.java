package com.jjsj.mall.front.service;

import com.jjsj.common.MyRecord;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.front.response.ConfirmOrderResponse;
import com.jjsj.mall.front.response.OrderDataResponse;
import com.jjsj.mall.front.response.StoreOrderDetailResponse;
import com.jjsj.mall.store.request.StoreProductReplyAddRequest;
import com.jjsj.mall.front.request.ConfirmOrderRequest;
import com.jjsj.mall.front.request.GetProductReply;
import com.jjsj.mall.front.request.OrderAgainRequest;
import com.jjsj.mall.front.request.OrderComputedRequest;
import com.jjsj.mall.front.request.OrderCreateRequest;
import com.jjsj.mall.front.request.OrderPayRequest;
import com.jjsj.mall.front.request.OrderRefundApplyRequest;
import com.jjsj.mall.front.vo.OrderAgainVo;
import java.util.HashMap;
import java.util.List;

/**
 * H5端订单操作
 */
public interface OrderService {

    /**
     * 订单确认
     *
     * @return 确认订单信息
     */
    ConfirmOrderResponse confirmOrder(ConfirmOrderRequest request);


    /**
     * 创建订单
     *
     * @param request 创建订单参数
     * @param key 订单key
     * @return MyRecord
     */
    MyRecord createOrder(OrderCreateRequest request, String key);

    /**
     * 再次下单
     *
     * @param request 参数
     * @return 下单结果
     */
    HashMap<String, Object> againOrder(OrderAgainRequest request);

    /**
     * 计算订单金额
     *
     * @param request 订单提交参数
     * @param orderKey 订单key
     */
    HashMap<String, Object> computedOrder(OrderComputedRequest request, String orderKey);

    /**
     * 支付
     *
     * @param request 支付参数
     * @return 支付结果
     */
    HashMap<String, Object> payOrder(OrderPayRequest request, String ip);

    /**
     * 订单列表
     *
     * @param type 类型
     * @param pageRequest 分页
     * @return 订单集合
     */
    List<OrderAgainVo> list(Integer type, PageParamRequest pageRequest);

    /**
     * 订单详情
     *
     * @param orderId 订单id
     */
    StoreOrderDetailResponse detailOrder(String orderId);

    /**
     * 订单状态数量
     *
     * @return 订单状态数据量
     */
    OrderDataResponse orderData();

    /**
     * 查询退款理由
     *
     * @return 退款理由集合
     */
    List<String> getRefundReason();

    Boolean delete(Integer id);

    boolean reply(StoreProductReplyAddRequest request);

    boolean take(Integer id);

    boolean cancel(Integer id);

    boolean refundApply(OrderRefundApplyRequest request);

    /**
     * 订单退款申请Task使用
     */
    Boolean refundApplyTask(List<OrderRefundApplyRequest> applyList);

    /**
     * 订单物流查看
     */
    Object expressOrder(String orderId);

    /**
     * 获取待评价商品信息
     *
     * @param getProductReply 订单详情参数
     * @return 待评价
     */
    Object getReplyProduct(GetProductReply getProductReply);

    /**
     * 更换支付类型
     *
     * @param payType 支付类型
     */
    boolean changePayType(String payType, String orderId);

}
