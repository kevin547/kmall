package com.jjsj.mall.marketing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.MyRecord;
import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageInfo;
import com.jjsj.mall.marketing.response.StoreCouponUserOrder;
import com.jjsj.mall.marketing.response.StoreCouponUserResponse;
import com.jjsj.mall.front.request.UserCouponReceiveRequest;
import com.jjsj.mall.marketing.model.StoreCouponUser;
import com.jjsj.mall.marketing.request.StoreCouponUserRequest;
import com.jjsj.mall.marketing.request.StoreCouponUserSearchRequest;
import com.jjsj.mall.store.model.StoreOrder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * StoreCouponUserService 接口

 */
public interface StoreCouponUserService extends IService<StoreCouponUser> {

    PageInfo<StoreCouponUserResponse> getList(StoreCouponUserSearchRequest request, PageParamRequest pageParamRequest);

    /**
     * 基本条件查询
     * @param storeCouponUser 基本参数
     * @return 查询的优惠券结果
     */
    List<StoreCouponUser> getList(StoreCouponUser storeCouponUser);

    Boolean receive(StoreCouponUserRequest storeCouponUserRequest);

    boolean use(Integer id, List<Integer> productIdList, BigDecimal price);

    /**
     * 检测优惠券是否可用，计算订单价格时使用
     * @param id            优惠券id
     * @param productIdList 商品id集合
     * @param price 价格
     * @return  可用状态
     */
    boolean canUse(Integer id, List<Integer> productIdList, BigDecimal price);

    boolean receiveAll(UserCouponReceiveRequest request, Integer userId, String type);

    boolean rollbackByCancelOrder(StoreOrder storeOrder);

    HashMap<Integer, StoreCouponUser> getMapByUserId(Integer userId);

    /**
     * 根据购物车id获取可用优惠券
     * @param cartIds 购物车id
     * @return 可用优惠券集合
     */
    List<StoreCouponUserOrder> getListByCartIds(List<Integer> cartIds);

    List<StoreCouponUserResponse> getListFront(Integer userId, PageParamRequest pageParamRequest);

    /**
     * 优惠券过期定时任务
     */
    void overdueTask();

    /**
     * 用户领取优惠券
     */
    Boolean receiveCoupon(UserCouponReceiveRequest request);

    /**
     * 支付成功赠送处理
     * @param couponId 优惠券编号
     * @param uid  用户uid
     * @return
     */
    MyRecord paySuccessGiveAway(Integer couponId, Integer uid);
}
