package com.jjsj.mall.log.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjsj.constants.Constants;
import com.jjsj.utils.DateUtil;
import com.jjsj.utils.RedisUtil;
import com.jjsj.utils.vo.dateLimitUtilVo;
import com.jjsj.mall.log.dao.StoreProductLogDao;
import com.jjsj.mall.log.model.StoreProductLog;
import com.jjsj.mall.log.service.StoreProductLogService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * StoreProductLogServiceImpl 接口实现
 */
@Service
public class StoreProductLogServiceImpl extends
    ServiceImpl<StoreProductLogDao, StoreProductLog> implements StoreProductLogService {

    private static final Logger logger = LoggerFactory.getLogger(StoreProductLogServiceImpl.class);
    @Resource
    private StoreProductLogDao dao;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Integer getCountByTimeAndType(String time, String type) {
        LambdaQueryWrapper<StoreProductLog> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StoreProductLog::getType, type);
        dateLimitUtilVo dateLimit = DateUtil.getDateLimit(time);
        //时间范围
        if (dateLimit.getStartTime() != null && dateLimit.getEndTime() != null) {
            Long startTime = DateUtil
                .dateStr2Timestamp(dateLimit.getStartTime(), Constants.DATE_TIME_TYPE_BEGIN);
            Long endTime = DateUtil
                .dateStr2Timestamp(dateLimit.getEndTime(), Constants.DATE_TIME_TYPE_END);
            lqw.between(StoreProductLog::getAddTime, startTime, endTime);
        }
        return dao.selectCount(lqw);
    }

    /**
     * task记录保存
     */
    @Override
    public void addLogTask() {
        Long size = redisUtil.getListSize(Constants.PRODUCT_LOG_KEY);
        logger.info("StoreProductLogServiceImpl.addLogTask | size:" + size);
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                //如果10秒钟拿不到一个数据，那么退出循环
                Object data = redisUtil.getRightPop(Constants.PRODUCT_LOG_KEY, 10L);
                if (null == data) {
                    continue;
                }
                try {
                    JSONObject jsonObject = JSON.parseObject(data.toString());
                    addLog(jsonObject);
                } catch (Exception e) {
                    redisUtil.lPush(Constants.PRODUCT_LOG_KEY, data);
                }
            }
        }
    }

    private void addLog(JSONObject jsonObject) {
        StoreProductLog proLog = new StoreProductLog();
        proLog.setProductId(jsonObject.getInteger("product_id"));
        proLog.setUid(jsonObject.getInteger("uid"));
        proLog.setType(jsonObject.getString("type"));
        proLog.setAddTime(jsonObject.getLong("add_time"));
        save(proLog);
    }

    /**
     * 列表
     * @param request 请求参数
     * @param pageParamRequest 分页类参数
     * @author kepler
     * @since 2020-12-01
     * @return List<StoreProductLog>
     */
//    @Override
//    public List<StoreProductLog> getList(StoreProductLogSearchRequest request, PageParamRequest pageParamRequest) {
//        PageHelper.startPage(pageParamRequest.getPageNum(), pageParamRequest.getPageSize());
//
//        //带 StoreProductLog 类的多条件查询
//        LambdaQueryWrapper<StoreProductLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        StoreProductLog model = new StoreProductLog();
//        BeanUtils.copyProperties(request, model);
//        lambdaQueryWrapper.setEntity(model);
//        return dao.selectList(lambdaQueryWrapper);
//    }

}

