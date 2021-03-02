package com.jjsj.mall.marketing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.marketing.response.StoreCouponFrontResponse;
import com.jjsj.mall.marketing.response.StoreCouponInfoResponse;
import com.jjsj.mall.marketing.model.StoreCoupon;
import com.jjsj.mall.marketing.request.StoreCouponRequest;
import com.jjsj.mall.marketing.request.StoreCouponSearchRequest;

import java.util.List;

/**
 * StoreCouponService 接口

 */
public interface StoreCouponService extends IService<StoreCoupon> {

    List<StoreCoupon> getList(StoreCouponSearchRequest request, PageParamRequest pageParamRequest);

    boolean create(StoreCouponRequest request);

    StoreCoupon getInfoException(Integer id);

    List<StoreCoupon> getReceiveListInId(List<Integer> couponId);

    void checkException(StoreCoupon storeCoupon);

//    List<StoreCoupon> getListByProductCanUse(Integer productId);

    StoreCouponInfoResponse info(Integer id);

    List<StoreCouponFrontResponse> getListByUser(Integer productId, PageParamRequest pageParamRequest, Integer userId);

    /**
     * 根据优惠券id获取
     * @param ids 优惠券id集合
     * @return
     */
    List<StoreCoupon> getByIds(List<Integer> ids);

    /**
     * 扣减数量
     * @param id 优惠券id
     * @param num 数量
     * @param isLimited 是否限量
     */
    Boolean deduction(Integer id, Integer num, Boolean isLimited);

    /**
     * 获取用户注册赠送新人券
     * @return
     */
    List<StoreCoupon> findRegisterList();
}
