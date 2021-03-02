package com.jjsj.mall.combination.service;

import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageInfo;
import com.jjsj.mall.combination.response.StorePinkAdminListResponse;
import com.jjsj.mall.combination.response.StorePinkDetailResponse;
import com.jjsj.mall.combination.model.StorePink;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.combination.request.StorePinkSearchRequest;

import java.util.List;

/**
 * StorePinkService

 */
public interface StorePinkService extends IService<StorePink> {

    /**
     * 获取拼团列表
     * @param request
     * @param pageParamRequest
     * @return
     */
    PageInfo<StorePinkAdminListResponse> getList(StorePinkSearchRequest request, PageParamRequest pageParamRequest);

    /**
     * 获取拼团列表Cid
     * @param cid 拼团商品id
     * @return
     */
    List<StorePink> getListByCid(Integer cid);

    /**
     * 实体查询
     * @param storePink
     * @return
     */
    List<StorePink> getByEntity(StorePink storePink);

    /**
     * PC拼团详情列表
     * @param pinkId 团长pinkId
     * @return
     */
    List<StorePinkDetailResponse> getAdminList(Integer pinkId);

    /**
     * 查询拼团列表
     * @param cid
     * @param kid
     */
    List<StorePink> getListByCidAndKid(Integer cid, Integer kid);

    /**
     * 根据团长拼团id获取拼团人数
     * @param pinkId
     * @return
     */
    Integer getCountByKid(Integer pinkId);

    /**
     * 检查状态，更新数据
     */
    void detectionStatus();

    /**
     * 拼团成功
     * @param kid
     * @return
     */
    boolean pinkSuccess(Integer kid);

    /**
     * 根据订单编号获取
     * @param orderId
     * @return
     */
    StorePink getByOrderId(String orderId);
}