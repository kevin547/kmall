package com.jjsj.mall.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jjsj.common.PageParamRequest;
import com.jjsj.constants.Constants;
import com.github.pagehelper.PageHelper;

import com.jjsj.utils.DateUtil;
import com.jjsj.mall.store.model.StoreOrderStatus;
import com.jjsj.mall.store.dao.StoreOrderStatusDao;
import com.jjsj.mall.store.request.StoreOrderStatusSearchRequest;
import com.jjsj.mall.store.service.StoreOrderStatusService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * StoreOrderStatusServiceImpl 接口实现

 */
@Service
public class StoreOrderStatusServiceImpl extends ServiceImpl<StoreOrderStatusDao, StoreOrderStatus> implements StoreOrderStatusService {

    @Resource
    private StoreOrderStatusDao dao;


    /**
    * 列表
    * @param request 请求参数
    * @param pageParamRequest 分页类参数
    *  @author kepler
    * @since 2020-05-28
    * @return List<StoreOrderStatus>
    */
    @Override
    public List<StoreOrderStatus> getList(StoreOrderStatusSearchRequest request, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());

        //带 StoreOrderStatus 类的多条件查询
        LambdaQueryWrapper<StoreOrderStatus> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        StoreOrderStatus model = new StoreOrderStatus();
        BeanUtils.copyProperties(request, model);
        lambdaQueryWrapper.setEntity(model);
        return dao.selectList(lambdaQueryWrapper);
    }

    /**
     * 保存订单退款记录
     * @param orderId 订单号
     * @param amount  金额
     * @param message  备注
     * @return {@link Boolean}
     */
    @Override
    public Boolean saveRefund(Integer orderId, BigDecimal amount, String message) {
        //此处更新订单状态
        String changeMessage = Constants.ORDER_LOG_MESSAGE_REFUND_PRICE.replace("{amount}", amount.toString());
        if(StringUtils.isNotBlank(message)){
            changeMessage += message;
        }
        StoreOrderStatus storeOrderStatus = new StoreOrderStatus();
        storeOrderStatus.setOid(orderId);
        storeOrderStatus.setChangeType(Constants.ORDER_LOG_REFUND_PRICE);
        storeOrderStatus.setChangeMessage(changeMessage);
        return save(storeOrderStatus);
    }

    /**
     * 创建记录日志
     * @param orderId Integer 订单号
     * @param type String 类型
     * @param message String 消息
     * @return Boolean
     */
    @Override
    public Boolean createLog(Integer orderId, String type, String message) {
        StoreOrderStatus storeOrderStatus = new StoreOrderStatus();
        storeOrderStatus.setOid(orderId);
        storeOrderStatus.setChangeType(type);
        storeOrderStatus.setChangeMessage(message);
        storeOrderStatus.setCreateTime(DateUtil.nowDateTime());
        return save(storeOrderStatus);
    }

    /**
     * 根据实体获取
     * @param storeOrderStatus 订单状态参数
     * @return 查询结果
     */
    @Override
    public List<StoreOrderStatus> getByEntity(StoreOrderStatus storeOrderStatus) {
        LambdaQueryWrapper<StoreOrderStatus> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.setEntity(storeOrderStatus);
        return dao.selectList(lambdaQueryWrapper);
    }

    /**
     * 根据订单id获取最后一条记录
     * @param orderId 订单id
     * @return
     */
    @Override
    public StoreOrderStatus getLastByOrderId(Integer orderId) {
        QueryWrapper<StoreOrderStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("oid", orderId);
        queryWrapper.orderByDesc("oid");
        queryWrapper.last(" limit 1");
        return dao.selectOne(queryWrapper);
    }

    public Boolean addLog(Integer orderId, String type, String message) {
        StoreOrderStatus storeOrderStatus = new StoreOrderStatus();
        storeOrderStatus.setOid(orderId);
        storeOrderStatus.setChangeType(type);
        storeOrderStatus.setChangeMessage(message);
        return save(storeOrderStatus);
    }
}

