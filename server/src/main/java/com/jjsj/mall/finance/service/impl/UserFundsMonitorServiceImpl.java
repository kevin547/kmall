package com.jjsj.mall.finance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jjsj.mall.finance.dao.UserFundsMonitorDao;
import com.jjsj.mall.finance.model.UserFundsMonitor;
import com.jjsj.mall.finance.request.FundsMonitorUserSearchRequest;
import com.jjsj.mall.finance.service.UserFundsMonitorService;
import com.jjsj.mall.user.model.UserBrokerageRecord;
import com.jjsj.mall.user.service.UserBrokerageRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
* UserRechargeServiceImpl 接口实现

*/
@Service
public class UserFundsMonitorServiceImpl extends ServiceImpl<UserFundsMonitorDao, UserFundsMonitor> implements UserFundsMonitorService {

    @Resource
    private UserFundsMonitorDao dao;

    @Autowired
    private UserBrokerageRecordService userBrokerageRecordService;

    /**
     * 佣金列表
     *  @author kepler
     * @since 2020-04-28
     * @return List<User>
     */
    @Override
    public List<UserFundsMonitor> getFundsMonitor(FundsMonitorUserSearchRequest request, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        HashMap<String, Object> map = new HashMap<>();
        String keywords = null;
        if(!StringUtils.isBlank(request.getKeywords())){
            keywords = "%"+request.getKeywords()+"%";
        }
        map.put("keywords", keywords);
        map.put("max", request.getMax());
        map.put("min", request.getMin());
        String sort =  "desc";
        if(!StringUtils.isBlank(request.getSort())){
            sort = request.getSort();
        }
        map.put("sort", sort);
        List<UserFundsMonitor> monitorList = dao.getFundsMonitor(map);
//        if (CollUtil.isEmpty(monitorList)) {
//            return monitorList;
//        }
//        List<Integer> spreadUidList = monitorList.stream().map(UserFundsMonitor::getSpreadUid).distinct().collect(Collectors.toList());
//        HashMap<Integer, User> mapListInUid = userService.getMapListInUid(spreadUidList);
//        for (UserFundsMonitor temp: monitorList) {
//            if (ObjectUtil.isNotNull(temp.getSpreadUid())) {
//                User user = mapListInUid.get(temp.getSpreadUid());
//                if (ObjectUtil.isNotNull(user)) {
//                    temp.setSpreadName(Optional.ofNullable(user.getNickname()).orElse(""));
//                }
//            }
//        }
        return monitorList;
    }

    /**
     * 佣金详细记录
     * @param uid 用户uid
     * @param pageParamRequest 分页参数
     * @return
     */
    @Override
    public PageInfo<UserBrokerageRecord> getFundsMonitorDetail(Integer uid, String dateLimit, PageParamRequest pageParamRequest) {
        return userBrokerageRecordService.getFundsMonitorDetail(uid, dateLimit, pageParamRequest);
    }


}

