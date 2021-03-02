package com.jjsj.mall.store.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageHelper;

import com.jjsj.mall.store.response.StoreCartResponse;
import com.jjsj.mall.store.model.StoreOrderInfo;
import com.jjsj.mall.store.dao.StoreOrderInfoDao;
import com.jjsj.mall.store.request.StoreOrderInfoSearchRequest;
import com.jjsj.mall.store.service.StoreOrderInfoService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjsj.mall.store.service.StoreProductReplyService;
import com.jjsj.mall.store.vo.StoreOrderInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * StoreOrderInfoServiceImpl 接口实现

 */
@Service
public class StoreOrderInfoServiceImpl extends ServiceImpl<StoreOrderInfoDao, StoreOrderInfo>
        implements StoreOrderInfoService {

    @Resource
    private StoreOrderInfoDao dao;

    @Autowired
    private StoreProductReplyService storeProductReplyService;


    /**
    * 列表
    * @param request 请求参数
    * @param pageParamRequest 分页类参数
    *  @author kepler
    * @since 2020-05-28
    * @return List<StoreOrderInfo>
    */
    @Override
    public List<StoreOrderInfo> getList(StoreOrderInfoSearchRequest request, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());

        //带 StoreOrderInfo 类的多条件查询
        LambdaQueryWrapper<StoreOrderInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        StoreOrderInfo model = new StoreOrderInfo();
        BeanUtils.copyProperties(request, model);
        lambdaQueryWrapper.setEntity(model);
        return dao.selectList(lambdaQueryWrapper);
    }

    /**
     * 根据id集合查询数据，返回 map
     * @param orderList List<Integer> id集合
     *  @author kepler
     * @since 2020-04-17
     * @return HashMap<Integer, StoreCart>
     */
    @Override
    public HashMap<Integer, List<StoreOrderInfoVo>> getMapInId(List<Integer> orderList){
        HashMap<Integer, List<StoreOrderInfoVo>> map = new HashMap<>();
        if(orderList.size() < 1){
            return map;
        }
        LambdaQueryWrapper<StoreOrderInfo> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.in(StoreOrderInfo::getOrderId, orderList);
        List<StoreOrderInfo> systemStoreStaffList = dao.selectList(lambdaQueryWrapper);
        if(systemStoreStaffList.size() < 1){
            return map;
        }
        for (StoreOrderInfo storeOrderInfo : systemStoreStaffList) {
            //解析商品详情JSON
            StoreOrderInfoVo StoreOrderInfoVo = new StoreOrderInfoVo();
            BeanUtils.copyProperties(storeOrderInfo, StoreOrderInfoVo, "info");
            StoreOrderInfoVo.setInfo(JSON.parseObject(storeOrderInfo.getInfo(), StoreCartResponse.class));
            if(map.containsKey(storeOrderInfo.getOrderId())){
                map.get(storeOrderInfo.getOrderId()).add(StoreOrderInfoVo);
            }else{
                List<StoreOrderInfoVo> storeOrderInfoVoList = new ArrayList<>();
                storeOrderInfoVoList.add(StoreOrderInfoVo);
                map.put(storeOrderInfo.getOrderId(), storeOrderInfoVoList);
            }
        }
        return map;
    }

    /**
     * 根据id集合查询数据，返回 map
     * @param orderId Integer id
     *  @author kepler
     * @since 2020-04-17
     * @return HashMap<Integer, StoreCart>
     */
    @Override
    public List<StoreOrderInfoVo> getOrderListByOrderId(Integer orderId){
        LambdaQueryWrapper<StoreOrderInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StoreOrderInfo::getOrderId, orderId);
        List<StoreOrderInfo> systemStoreStaffList = dao.selectList(lambdaQueryWrapper);
        if(systemStoreStaffList.size() < 1){
            return null;
        }

        List<StoreOrderInfoVo> storeOrderInfoVoList = new ArrayList<>();
        for (StoreOrderInfo storeOrderInfo : systemStoreStaffList) {
            //解析商品详情JSON
            StoreOrderInfoVo storeOrderInfoVo = new StoreOrderInfoVo();
            BeanUtils.copyProperties(storeOrderInfo, storeOrderInfoVo, "info");
            storeOrderInfoVo.setInfo(JSON.parseObject(storeOrderInfo.getInfo(),StoreCartResponse.class));
            storeOrderInfoVo.getInfo().setIsReply(
                    storeProductReplyService.isReply(storeOrderInfoVo.getUnique(), storeOrderInfoVo.getOrderId()) ? 1 : 0
            );
            storeOrderInfoVoList.add(storeOrderInfoVo);
        }
        return storeOrderInfoVoList;
    }

    /**
     * 新增订单详情
     * @param storeOrderInfos 订单详情集合
     * @return 订单新增结果
     */
    @Override
    public boolean saveOrderInfos(List<StoreOrderInfo> storeOrderInfos) {
        return saveBatch(storeOrderInfos);
    }
}

