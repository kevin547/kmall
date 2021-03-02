package com.jjsj.mall.front.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjsj.common.CommonPage;
import com.jjsj.common.MyRecord;
import com.jjsj.common.PageParamRequest;
import com.jjsj.constants.BrokerageRecordConstants;
import com.jjsj.constants.Constants;
import com.jjsj.constants.PayConstants;
import com.jjsj.exception.MallException;
import com.github.pagehelper.PageInfo;
import com.jjsj.mall.finance.model.UserRecharge;
import com.jjsj.mall.finance.request.FundsMonitorSearchRequest;
import com.jjsj.mall.finance.request.UserExtractRequest;
import com.jjsj.mall.finance.service.UserExtractService;
import com.jjsj.mall.finance.service.UserRechargeService;
import com.jjsj.mall.front.response.LoginResponse;
import com.jjsj.mall.front.response.OrderPayResultResponse;
import com.jjsj.mall.front.response.SpreadCommissionDetailResponse;
import com.jjsj.mall.front.response.UserBalanceResponse;
import com.jjsj.mall.front.response.UserCommissionResponse;
import com.jjsj.mall.front.response.UserExtractCashResponse;
import com.jjsj.mall.front.response.UserExtractRecordResponse;
import com.jjsj.mall.front.response.UserRechargeBillRecordResponse;
import com.jjsj.mall.front.response.UserRechargeItemResponse;
import com.jjsj.mall.front.response.UserRechargeResponse;
import com.jjsj.mall.front.response.UserSpreadBannerResponse;
import com.jjsj.mall.front.response.UserSpreadCommissionResponse;
import com.jjsj.mall.front.response.UserSpreadOrderItemChildResponse;
import com.jjsj.mall.front.response.UserSpreadOrderItemResponse;
import com.jjsj.mall.front.response.UserSpreadOrderResponse;
import com.jjsj.mall.front.response.UserSpreadPeopleItemResponse;
import com.jjsj.mall.front.response.UserSpreadPeopleResponse;
import com.jjsj.mall.marketing.model.StoreCoupon;
import com.jjsj.mall.marketing.model.StoreCouponUser;
import com.jjsj.mall.marketing.service.StoreCouponService;
import com.jjsj.mall.marketing.service.StoreCouponUserService;
import com.jjsj.mall.payment.service.RechargePayService;
import com.jjsj.mall.payment.wechat.WeChatPayService;
import com.jjsj.mall.store.model.StoreOrder;
import com.jjsj.mall.store.service.StoreOrderService;
import com.jjsj.mall.system.model.SystemUserLevel;
import com.jjsj.mall.system.request.SystemUserLevelSearchRequest;
import com.jjsj.mall.system.service.SystemConfigService;
import com.jjsj.mall.system.service.SystemGroupDataService;
import com.jjsj.mall.system.service.SystemUserLevelService;
import com.jjsj.mall.system.vo.SystemGroupDataRechargeConfigVo;
import com.jjsj.mall.wechat.response.WeChatAuthorizeLoginGetOpenIdResponse;
import com.jjsj.mall.wechat.response.WeChatAuthorizeLoginUserInfoResponse;
import com.jjsj.mall.wechat.response.WeChatProgramAuthorizeLoginGetOpenIdResponse;
import com.jjsj.mall.wechat.service.WeChatService;
import com.jjsj.utils.MallUtil;
import com.jjsj.utils.DateUtil;
import com.jjsj.mall.front.request.UserRechargeRequest;
import com.jjsj.mall.front.request.UserSpreadPeopleRequest;
import com.jjsj.mall.front.service.UserCenterService;
import com.jjsj.mall.front.vo.WxPayJsResultVo;
import com.jjsj.mall.user.dao.UserDao;
import com.jjsj.mall.user.model.User;
import com.jjsj.mall.user.model.UserBill;
import com.jjsj.mall.user.model.UserBrokerageRecord;
import com.jjsj.mall.user.model.UserToken;
import com.jjsj.mall.user.request.RegisterThirdUserRequest;
import com.jjsj.mall.user.service.UserBillService;
import com.jjsj.mall.user.service.UserBrokerageRecordService;
import com.jjsj.mall.user.service.UserService;
import com.jjsj.mall.user.service.UserTokenService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;


/**
 * 用户中心 服务实现类 
 */
@Service
public class UserCenterServiceImpl extends ServiceImpl<UserDao, User> implements UserCenterService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserBillService userBillService;

    @Autowired
    private UserExtractService userExtractService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private SystemUserLevelService systemUserLevelService;

    @Autowired
    private SystemGroupDataService systemGroupDataService;

    @Autowired
    private StoreOrderService storeOrderService;

    @Autowired
    private UserRechargeService userRechargeService;

    @Autowired
    private RechargePayService rechargePayService;

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private WeChatService weChatService;

    @Autowired
    private UserBrokerageRecordService userBrokerageRecordService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private WeChatPayService weChatPayService;

    @Autowired
    private StoreCouponService storeCouponService;

    @Autowired
    private StoreCouponUserService storeCouponUserService;


    /**
     * 推广数据接口(昨天的佣金 累计提现金额 当前佣金)
     */
    @Override
    public UserCommissionResponse getCommission() {
        User user = userService.getInfo();
        if (ObjectUtil.isNull(user)) {
            throw new MallException("用户数据异常");
        }

        // 昨天得佣金
        BigDecimal yesterdayIncomes = userBrokerageRecordService.getYesterdayIncomes(user.getUid());
        //累计已提取佣金
        BigDecimal totalMoney = userExtractService
            .getExtractTotalMoney(userService.getUserIdException());

        UserCommissionResponse userCommissionResponse = new UserCommissionResponse();
        userCommissionResponse.setLastDayCount(yesterdayIncomes);
        userCommissionResponse.setExtractCount(totalMoney);
        userCommissionResponse.setCommissionCount(user.getBrokeragePrice());
        return userCommissionResponse;
    }

    /**
     * 推广佣金明细
     *
     * @return UserSpreadCommissionResponse
     *  @author kepler
     * @since 2020-06-08
     */
    @Override
    public PageInfo<UserSpreadCommissionResponse> getSpreadCommissionByType(int type,
        PageParamRequest pageParamRequest) {
        String category = Constants.USER_BILL_CATEGORY_MONEY;
        List<String> typeList = CollUtil.newArrayList();
        switch (type) {
            case 0:
                typeList.add(Constants.USER_BILL_TYPE_RECHARGE);
                typeList.add(Constants.USER_BILL_TYPE_PAY_MONEY);
                typeList.add(Constants.USER_BILL_TYPE_SYSTEM_ADD);
                typeList.add(Constants.USER_BILL_TYPE_PAY_PRODUCT_REFUND);
                typeList.add(Constants.USER_BILL_TYPE_SYSTEM_SUB);
                typeList.add(Constants.USER_BILL_TYPE_PAY_MEMBER);
                typeList.add(Constants.USER_BILL_TYPE_OFFLINE_SCAN);
                break;
            case 1:
                typeList.add(Constants.USER_BILL_TYPE_PAY_MONEY);
                typeList.add(Constants.USER_BILL_TYPE_PAY_MEMBER);
                typeList.add(Constants.USER_BILL_TYPE_OFFLINE_SCAN);
                typeList.add(Constants.USER_BILL_TYPE_USER_RECHARGE_REFUND);
                break;
            case 2:
                typeList.add(Constants.USER_BILL_TYPE_RECHARGE);
                typeList.add(Constants.USER_BILL_TYPE_SYSTEM_ADD);
                break;
            case 3:
                category = Constants.USER_BILL_CATEGORY_BROKERAGE_PRICE;
                typeList.add(Constants.USER_BILL_TYPE_BROKERAGE);
                break;
            case 4:
                typeList.add(Constants.USER_BILL_TYPE_EXTRACT);
                break;
            default:
                break;

        }
        return userBillService
            .getListGroupByMonth(userService.getUserIdException(), typeList, pageParamRequest,
                category);
    }

    /**
     * 推广佣金/提现总和
     *
     * @return BigDecimal
     *  @author kepler
     * @since 2020-06-08
     */
    @Override
    public BigDecimal getSpreadCountByType(int type) {
        //推广佣金/提现总和
        Integer userId = userService.getUserIdException();
        if (type == 3) {
            BigDecimal count = userBillService
                .getSumBigDecimal(null, userId, Constants.USER_BILL_CATEGORY_MONEY, null,
                    Constants.USER_BILL_TYPE_BROKERAGE);
            BigDecimal withdraw = userBillService
                .getSumBigDecimal(1, userId, Constants.USER_BILL_CATEGORY_MONEY, null,
                    Constants.USER_BILL_TYPE_BROKERAGE); //提现
            return count.subtract(withdraw);
        }

        //累计提现
        if (type == 4) {
            return userExtractService.getWithdrawn(null, null);
        }

        return BigDecimal.ZERO;
    }

    /**
     * 提现申请
     *
     * @return Boolean
     *  @author kepler
     * @since 2020-06-08
     */
    @Override
    public Boolean extractCash(UserExtractRequest request) {
        switch (request.getExtractType()) {
            case "weixin":
                if (StringUtils.isBlank(request.getWechat())) {
                    throw new MallException("请填写微信号！");
                }
                request.setAlipayCode(null);
                request.setBankCode(null);
                request.setBankName(null);
                break;
            case "alipay":
                if (StringUtils.isBlank(request.getAlipayCode())) {
                    throw new MallException("请填写支付宝账号！");
                }
                request.setWechat(null);
                request.setBankCode(null);
                request.setBankName(null);
                break;
            case "bank":
                if (StringUtils.isBlank(request.getBankName())) {
                    throw new MallException("请填写银行名称！");
                }
                if (StringUtils.isBlank(request.getBankCode())) {
                    throw new MallException("请填写银行卡号！");
                }
                request.setWechat(null);
                request.setAlipayCode(null);
                break;
            default:
                throw new MallException("请选择支付方式");
        }
        return userExtractService.extractApply(request);
//        return userExtractService.create(request, userService.getUserIdException());
    }

    /**
     * 提现银行/提现最低金额
     *
     * @return UserExtractCashResponse
     *  @author kepler
     * @since 2020-06-08
     */
    @Override
    public UserExtractCashResponse minExtractCash() {

        // 提现最低金额
        String minPrice = systemConfigService
            .getValueByKeyException(Constants.CONFIG_EXTRACT_MIN_PRICE);
        // 冻结天数
        String extractTime = systemConfigService
            .getValueByKey(Constants.CONFIG_EXTRACT_FREEZING_TIME);
        // 可提现佣金
        BigDecimal brokeragePrice = userService.getInfo().getBrokeragePrice();
        // 冻结佣金
        BigDecimal freeze = userBrokerageRecordService
            .getFreezePrice(userService.getUserIdException());
        // 获取提现银行
        String bank = systemConfigService.getValueByKeyException(Constants.CONFIG_BANK_LIST)
            .replace("\r\n", "\n");
        List<String> bankArr = new ArrayList<>();
        if (bank.indexOf("\n") > 0) {
            for (String s : bank.split("\n")) {
                bankArr.add(s);
            }
        } else {
            bankArr.add(bank);
        }
        return new UserExtractCashResponse(
            bankArr,
            minPrice,
            brokeragePrice,
            freeze,
            extractTime
        );
    }

    /**
     * 会员等级列表
     *
     * @return List<UserLevel>
     *  @author kepler
     * @since 2020-06-19
     */
    @Override
    public List<SystemUserLevel> getUserLevelList() {
        SystemUserLevelSearchRequest request = new SystemUserLevelSearchRequest();
        request.setIsShow(true);
        request.setIsDel(false);
        return systemUserLevelService.getList(request, new PageParamRequest());
    }

    /**
     * 推广用户， 我自己推广了哪些用户
     *
     * @return UserSpreadPeopleResponse
     *  @author kepler
     * @since 2020-05-18
     */
    @Override
    public UserSpreadPeopleResponse getSpreadPeopleList(UserSpreadPeopleRequest request,
        PageParamRequest pageParamRequest) {
        //查询当前用户名下的一级推广员
        UserSpreadPeopleResponse userSpreadPeopleResponse = new UserSpreadPeopleResponse();
        List<Integer> userIdList = new ArrayList<>();
        userIdList.add(userService.getUserIdException());
        userIdList = userService.getSpreadPeopleIdList(userIdList); //我推广的一级用户id集合

        if (CollUtil.isEmpty(userIdList)) {//如果没有一级推广人，直接返回
            userSpreadPeopleResponse.setCount(0);
            userSpreadPeopleResponse.setTotal(0);
            userSpreadPeopleResponse.setTotalLevel(0);
            return userSpreadPeopleResponse;
        }

        userSpreadPeopleResponse.setTotal(userIdList.size()); //一级推广人
        //查询二级推广人
        List<Integer> secondSpreadIdList = CollUtil.newArrayList();
        if (CollUtil.isNotEmpty(userIdList)) {
            secondSpreadIdList = userService.getSpreadPeopleIdList(userIdList);
        }
        userSpreadPeopleResponse.setTotalLevel(secondSpreadIdList.size());
        userSpreadPeopleResponse.setCount(userIdList.size() + secondSpreadIdList.size());

        if (request.getGrade() == 1) {
            //二级推广人
//            userIdList.addAll(userService.getSpreadPeopleIdList(userIdList));
//            userSpreadPeopleResponse.setTotalLevel(userIdList.size()); //把二级推广人的id追加到一级推广人集合中
            userIdList.clear();
            userIdList.addAll(secondSpreadIdList);
        }

        if (userIdList.size() > 0) {
            List<UserSpreadPeopleItemResponse> userSpreadPeopleItemResponseList = userService
                .getSpreadPeopleList(userIdList, request.getKeyword(), request.getSortKey(),
                    request.getIsAsc(), pageParamRequest);
            userSpreadPeopleResponse.setSpreadPeopleList(userSpreadPeopleItemResponseList);
        }

        return userSpreadPeopleResponse;
    }

    /**
     * 积分记录
     *
     * @return List<UserBill>
     *  @author kepler
     * @since 2020-06-10
     */
    @Override
    public List<UserBill> getUserBillList(String category, PageParamRequest pageParamRequest) {
        FundsMonitorSearchRequest request = new FundsMonitorSearchRequest();
        request.setUid(userService.getUserIdException());
        request.setCategory(category);
        return userBillService.getList(request, pageParamRequest);
    }

    /**
     * 充值额度选择
     *
     * @return UserRechargeResponse
     *  @author kepler
     * @since 2020-06-10
     */
    @Override
    public UserRechargeResponse getRechargeConfig() {
        UserRechargeResponse userRechargeResponse = new UserRechargeResponse();
        userRechargeResponse.setRechargeQuota(systemGroupDataService
            .getListByGid(Constants.GROUP_DATA_ID_RECHARGE_LIST, UserRechargeItemResponse.class));
        String rechargeAttention = systemConfigService
            .getValueByKey(Constants.CONFIG_RECHARGE_ATTENTION);
        List<String> rechargeAttentionList = new ArrayList<>();
        if (StringUtils.isNotBlank(rechargeAttention)) {
            rechargeAttentionList = MallUtil.stringToArrayStrRegex(rechargeAttention, "\n");
        }
        userRechargeResponse.setRechargeAttention(rechargeAttentionList);
        return userRechargeResponse;
    }

    /**
     * 用户资金统计
     *
     * @return UserBalanceResponse
     *  @author kepler
     * @since 2020-06-10
     */
    @Override
    public UserBalanceResponse getUserBalance() {
        User info = userService.getInfo();
        BigDecimal recharge = userBillService
            .getSumBigDecimal(1, info.getUid(), Constants.USER_BILL_CATEGORY_MONEY, null, null);
        BigDecimal orderStatusSum = storeOrderService.getSumBigDecimal(info.getUid(), null);
        return new UserBalanceResponse(info.getNowMoney(), recharge, orderStatusSum);
    }

//    /**
//     * 推广订单
//     *  @author kepler
//     * @since 2020-06-10
//     * @return UserSpreadOrderResponse;
//     */
//    @Override
//    public UserSpreadOrderResponse getSpreadOrder(PageParamRequest pageParamRequest) {
//        UserSpreadOrderResponse userSpreadOrderResponse = new UserSpreadOrderResponse();
//        Integer userId = userService.getUserIdException();
//        List<Integer> userIdList = new ArrayList<>();
//        userIdList.add(userId);
//        userIdList = userService.getSpreadPeopleIdList(userIdList); //拿到一级推广id
//        if(null == userIdList){
//            return userSpreadOrderResponse;
//        }
//        //查询所有推广人的由于订单获取佣金记录，分页
//
//        FundsMonitorSearchRequest request = new FundsMonitorSearchRequest();
////        request.setCategory(Constants.USER_BILL_CATEGORY_MONEY);
////        request.setType(Constants.USER_BILL_TYPE_PAY_MONEY);
//        request.setCategory(Constants.USER_BILL_CATEGORY_BROKERAGE_PRICE);
//        request.setType(Constants.USER_BILL_TYPE_BROKERAGE);
//        request.setUserIdList(CollUtil.newArrayList(userId));
//        request.setLinkId("gt");
//        request.setPm(1);
//        List<UserBill> list = userBillService.getList(request, pageParamRequest);
//        if(null == list){
//            return userSpreadOrderResponse;
//        }
//        CommonPage<UserBill> userBillCommonPage = CommonPage.restPage(list); //拿到分页信息
//        userSpreadOrderResponse.setCount(userBillCommonPage.getTotal()); //总数
//
//        //拿到订单id, 查询订单信息
//        List<Integer> orderIdList = list.stream().map(i -> Integer.parseInt(i.getLinkId())).distinct().collect(Collectors.toList());
//        Map<Integer, StoreOrder> orderList = storeOrderService.getMapInId(orderIdList);
//
//        //用户信息
////        userIdList = list.stream().map(UserBill::getUid).distinct().collect(Collectors.toList());
//        List<StoreOrder> storeOrderList = new ArrayList<>(orderList.values());
//        userIdList = storeOrderList.stream().map(StoreOrder::getUid).distinct().collect(Collectors.toList());
//        HashMap<Integer, User> userList = userService.getMapListInUid(userIdList);
//
//        //按时间分组数据
//        List<UserSpreadOrderItemResponse> userSpreadOrderItemResponseList = new ArrayList<>();
//        for (UserBill userBill : list) {
//            String date = DateUtil.dateToStr(userBill.getCreateTime(), Constants.DATE_FORMAT_MONTH);
//            boolean isAdd = false;
//            String orderId = "";
//            Integer linkId = Integer.parseInt(userBill.getLinkId());
//            if(null != orderList && orderList.containsKey(linkId)){
//                orderId = orderList.get(linkId).getOrderId();
//            }
//
//            UserSpreadOrderItemChildResponse userSpreadOrderItemChildResponse = new UserSpreadOrderItemChildResponse(
//                    orderId, //订单号
//                    userBill.getCreateTime(),
//                    (userBill.getStatus() == 1) ? userBill.getNumber() : BigDecimal.ZERO,
////                    userList.get(userBill.getUid()).getAvatar(),
////                    userList.get(userBill.getUid()).getNickname(),
//                    userList.get(orderList.get(linkId).getUid()).getAvatar(),
//                    userList.get(orderList.get(linkId).getUid()).getNickname(),
//                    userBill.getType()
//            );
//
//            //如果在已有的数据中找到当前月份数据则追加
//            for (UserSpreadOrderItemResponse userSpreadOrderItemResponse: userSpreadOrderItemResponseList) {
//                if(userSpreadOrderItemResponse.getTime().equals(date)){
//                    userSpreadOrderItemResponse.getChild().add(userSpreadOrderItemChildResponse);
//                    isAdd = true;
//                    break;
//                }
//            }
//
//            //没月找到则创建一条数据
//            if(!isAdd){
//                //创建一个
//                UserSpreadOrderItemResponse userSpreadOrderItemResponse = new UserSpreadOrderItemResponse();
//                userSpreadOrderItemResponse.setTime(date);
//                userSpreadOrderItemResponse.getChild().add(userSpreadOrderItemChildResponse);
//                userSpreadOrderItemResponseList.add(userSpreadOrderItemResponse);
//            }
//        }
//
//        List<String> monthList = userSpreadOrderItemResponseList.stream().map(s -> "'" + s.getTime() + "'").collect(Collectors.toList());
//
//        if(monthList.size() < 1){
//            return userSpreadOrderResponse;
//        }
//
//        //获取每个月分的总数
//        Map<String, Integer> countMap = userBillService.getCountListByMonth(request, monthList);
//
//        //统计每月的订单数量
//        for (UserSpreadOrderItemResponse userSpreadOrderItemResponse: userSpreadOrderItemResponseList) {
//            userSpreadOrderItemResponse.setCount(countMap.get(userSpreadOrderItemResponse.getTime()));
//        }
//
//        userSpreadOrderResponse.setList(userSpreadOrderItemResponseList);
//
//        return userSpreadOrderResponse;
//    }

    /**
     * 推广订单
     *
     * @return UserSpreadOrderResponse;
     */
    @Override
    public UserSpreadOrderResponse getSpreadOrder(PageParamRequest pageParamRequest) {
        User user = userService.getInfo();
        if (ObjectUtil.isNull(user)) {
            throw new MallException("用户数据异常");
        }
        UserSpreadOrderResponse spreadOrderResponse = new UserSpreadOrderResponse();
        // 获取累计推广条数
        Integer spreadCount = userBrokerageRecordService.getSpreadCountByUid(user.getUid());
        spreadOrderResponse.setCount(spreadCount.longValue());
        if (spreadCount.equals(0)) {
            return spreadOrderResponse;
        }

        // 获取推广订单记录，分页
        List<UserBrokerageRecord> recordList = userBrokerageRecordService
            .findSpreadListByUid(user.getUid(), pageParamRequest);
        // 获取对应的订单信息
        List<String> orderNoList = recordList.stream().map(UserBrokerageRecord::getLinkId)
            .collect(Collectors.toList());
        Map<String, StoreOrder> orderMap = storeOrderService.getMapInOrderNo(orderNoList);
        // 获取对应的用户信息
        List<StoreOrder> storeOrderList = new ArrayList<>(orderMap.values());
        List<Integer> uidList = storeOrderList.stream().map(StoreOrder::getUid).distinct()
            .collect(Collectors.toList());
        HashMap<Integer, User> userMap = userService.getMapListInUid(uidList);

        List<UserSpreadOrderItemResponse> userSpreadOrderItemResponseList = new ArrayList<>();
        List<String> monthList = CollUtil.newArrayList();
        recordList.forEach(record -> {
            UserSpreadOrderItemChildResponse userSpreadOrderItemChildResponse = new UserSpreadOrderItemChildResponse();
            userSpreadOrderItemChildResponse.setOrderId(record.getLinkId());
            userSpreadOrderItemChildResponse.setTime(record.getUpdateTime());
            userSpreadOrderItemChildResponse.setNumber(record.getPrice());
            Integer orderUid = orderMap.get(record.getLinkId()).getUid();
            userSpreadOrderItemChildResponse.setAvatar(userMap.get(orderUid).getAvatar());
            userSpreadOrderItemChildResponse.setNickname(userMap.get(orderUid).getNickname());
            userSpreadOrderItemChildResponse.setType("返佣");

            String month = DateUtil.dateToStr(record.getUpdateTime(), Constants.DATE_FORMAT_MONTH);
            if (monthList.contains(month)) {
                //如果在已有的数据中找到当前月份数据则追加
                for (UserSpreadOrderItemResponse userSpreadOrderItemResponse : userSpreadOrderItemResponseList) {
                    if (userSpreadOrderItemResponse.getTime().equals(month)) {
                        userSpreadOrderItemResponse.getChild()
                            .add(userSpreadOrderItemChildResponse);
                        break;
                    }
                }
            } else {// 不包含此月份
                //创建一个
                UserSpreadOrderItemResponse userSpreadOrderItemResponse = new UserSpreadOrderItemResponse();
                userSpreadOrderItemResponse.setTime(month);
                userSpreadOrderItemResponse.getChild().add(userSpreadOrderItemChildResponse);
                userSpreadOrderItemResponseList.add(userSpreadOrderItemResponse);
                monthList.add(month);
            }
        });

        // 获取月份总订单数
        Map<String, Integer> countMap = userBrokerageRecordService
            .getSpreadCountByUidAndMonth(user.getUid(), monthList);
        for (UserSpreadOrderItemResponse userSpreadOrderItemResponse : userSpreadOrderItemResponseList) {
            userSpreadOrderItemResponse
                .setCount(countMap.get(userSpreadOrderItemResponse.getTime()));
        }

        spreadOrderResponse.setList(userSpreadOrderItemResponseList);
        return spreadOrderResponse;
    }

    /**
     * 充值
     *
     * @return UserSpreadOrderResponse;
     *  @author kepler
     * @since 2020-06-10
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, MallException.class})
    public OrderPayResultResponse recharge(UserRechargeRequest request) {
        request.setPayType(Constants.PAY_TYPE_WE_CHAT);

        //验证金额是否为最低金额
        String rechargeMinAmountStr = systemConfigService
            .getValueByKeyException(Constants.CONFIG_KEY_RECHARGE_MIN_AMOUNT);
        BigDecimal rechargeMinAmount = new BigDecimal(rechargeMinAmountStr);
        int compareResult = rechargeMinAmount.compareTo(request.getPrice());
        if (compareResult > 0) {
            throw new MallException("充值金额不能低于" + rechargeMinAmountStr);
        }

        request.setGivePrice(BigDecimal.ZERO);

        if (request.getGroupDataId() > 0) {
            SystemGroupDataRechargeConfigVo systemGroupData = systemGroupDataService
                .getNormalInfo(request.getGroupDataId(), SystemGroupDataRechargeConfigVo.class);
            if (null == systemGroupData) {
                throw new MallException("您选择的充值方式已下架");
            }

            //售价和赠送
            request.setPrice(systemGroupData.getPrice());
            request.setGivePrice(systemGroupData.getGiveMoney());

        }
        User currentUser = userService.getInfoException();
        //生成系统订单

        UserRecharge userRecharge = new UserRecharge();
        userRecharge.setUid(currentUser.getUid());
        userRecharge.setOrderId(MallUtil.getOrderNo("recharge"));
        userRecharge.setPrice(request.getPrice());
        userRecharge.setGivePrice(request.getGivePrice());
        userRecharge.setRechargeType(request.getFromType());
        boolean save = userRechargeService.save(userRecharge);
        if (!save) {
            throw new MallException("生成充值订单失败!");
        }

        OrderPayResultResponse response = new OrderPayResultResponse();
        MyRecord record = new MyRecord();
        Map<String, String> unifiedorder = weChatPayService
            .unifiedRecharge(userRecharge, request.getClientIp());
        record.set("status", true);
        response.setStatus(true);
        WxPayJsResultVo vo = new WxPayJsResultVo();
        vo.setAppId(unifiedorder.get("appId"));
        vo.setNonceStr(unifiedorder.get("nonceStr"));
        vo.setPackages(unifiedorder.get("package"));
        vo.setSignType(unifiedorder.get("signType"));
        vo.setTimeStamp(unifiedorder.get("timeStamp"));
        vo.setPaySign(unifiedorder.get("paySign"));
        if (userRecharge.getRechargeType().equals(PayConstants.PAY_CHANNEL_WE_CHAT_H5)) {
            vo.setMwebUrl(unifiedorder.get("mweb_url"));
            response.setPayType(PayConstants.PAY_CHANNEL_WE_CHAT_H5);
        }
        response.setJsConfig(vo);
        response.setOrderNo(userRecharge.getOrderId());
        return response;
    }

    /**
     * 微信登录
     *
     * @return UserSpreadOrderResponse;
     *  @author kepler
     * @since 2020-06-10
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, MallException.class})
    public LoginResponse weChatAuthorizeLogin(String code, Integer spreadUid) {
        try {
            // 通过code获取获取公众号授权信息
            WeChatAuthorizeLoginGetOpenIdResponse response = weChatService.authorizeLogin(code);
            // 获取登录用户
            User user = publicLogin(response.getOpenId(), response.getAccessToken(), spreadUid);
            //通过用户id获取登录token信息
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(userService.token(user));
            user.setPwd(null);
            loginResponse.setUser(user);

            // 记录最后一次登录时间
            user.setLastLoginTime(DateUtil.nowDateTime());
            updateById(user);
            return loginResponse;
        } catch (Exception e) {
            throw new MallException(e.getMessage());
        }
    }

    /**
     * 通过openId登录
     *
     * @param openId String 微信公众号通过code取回的openId对象
     * @param token String code换取的token
     * @return List<LoginResponse>
     *  @author kepler
     * @since 2020-05-25
     */
    private User publicLogin(String openId, String token, Integer spreadUid) {
        try {
            //检测是否存在
            UserToken userToken = userTokenService
                .checkToken(openId, Constants.THIRD_LOGIN_TOKEN_TYPE_PUBLIC);
            if (null != userToken) {
                User user = userService.getById(userToken.getUid());
                // 分销绑定
                if (userService.checkBingSpread(user, spreadUid, "old")) {
                    user.setSpreadUid(spreadUid);
                    user.setSpreadTime(DateUtil.nowDateTime());
                    Boolean execute = transactionTemplate.execute(e -> {
                        userService.updateById(user);
                        userService.updateSpreadCountByUid(spreadUid);
                        return Boolean.TRUE;
                    });
                    if (!execute) {
                        System.out.println(StrUtil
                            .format("公众号登录绑定分销关系失败，uid={},spreadUid={}", user.getUid(), spreadUid));
                    }
                }
                return user;
            }

            //没有注册， 获取微信用户信息， 小程序需要前端传入用户信息参数
            WeChatAuthorizeLoginUserInfoResponse userInfo = weChatService
                .getUserInfo(openId, token);
            RegisterThirdUserRequest registerThirdUserRequest = new RegisterThirdUserRequest();
            BeanUtils.copyProperties(userInfo, registerThirdUserRequest);
            String unionId = userInfo.getUnionId();

            //看unionid是否已经绑定
            if (StringUtils.isNotBlank(unionId)) {
                userToken = userTokenService
                    .checkToken(userInfo.getUnionId(), Constants.THIRD_LOGIN_TOKEN_TYPE_UNION_ID);
                if (null != userToken) {
                    User user = userService.getById(userToken.getUid());
                    // 分销绑定
                    if (userService.checkBingSpread(user, spreadUid, "old")) {
                        user.setSpreadUid(spreadUid);
                        user.setSpreadTime(DateUtil.nowDateTime());
                        Boolean execute = transactionTemplate.execute(e -> {
                            userService.updateById(user);
                            userService.updateSpreadCountByUid(spreadUid);
                            return Boolean.TRUE;
                        });
                        if (!execute) {
                            System.out.println(StrUtil
                                .format("公众号登录绑定分销关系失败，uid={},spreadUid={}", user.getUid(),
                                    spreadUid));
                        }
                    }
                    return user;
                }
            }

            //TODO 是否需要强制注册用户，1 强制注册，2 需要返回数据给前端，让其输入手机号和验证码
            User user = userService
                .registerByThird(registerThirdUserRequest, Constants.USER_LOGIN_TYPE_PUBLIC);

            userTokenService.bind(openId, Constants.THIRD_LOGIN_TOKEN_TYPE_PUBLIC, user.getUid());
            if (StringUtils.isNotBlank(unionId)) {
                //有就绑定
                userTokenService
                    .bind(unionId, Constants.THIRD_LOGIN_TOKEN_TYPE_UNION_ID, user.getUid());
            }
            // 分销绑定
            if (userService.checkBingSpread(user, spreadUid, "new")) {
                user.setSpreadUid(spreadUid);
                user.setSpreadTime(DateUtil.nowDateTime());
                Boolean execute = transactionTemplate.execute(e -> {
                    userService.updateById(user);
                    userService.updateSpreadCountByUid(spreadUid);
                    return Boolean.TRUE;
                });
                if (!execute) {
                    System.out.println(StrUtil
                        .format("公众号登录绑定分销关系失败，uid={},spreadUid={}", user.getUid(), spreadUid));
                }
            }

            // 赠送优惠券
            // 查询是否有新人注册赠送优惠券
            List<StoreCouponUser> couponUserList = CollUtil.newArrayList();
            List<StoreCoupon> couponList = storeCouponService.findRegisterList();
            if (CollUtil.isNotEmpty(couponList)) {
                couponList.forEach(storeCoupon -> {
                    StoreCouponUser storeCouponUser = new StoreCouponUser();
                    storeCouponUser.setCouponId(storeCoupon.getId());
//                storeCouponUser.setUid(userId);
                    storeCouponUser.setName(storeCoupon.getName());
                    storeCouponUser.setMoney(storeCoupon.getMoney());
                    storeCouponUser.setMinPrice(storeCoupon.getMinPrice());
                    storeCouponUser.setStartTime(storeCoupon.getUseStartTime());
                    storeCouponUser.setEndTime(storeCoupon.getUseEndTime());
                    storeCouponUser.setUseType(storeCoupon.getUseType());
                    storeCouponUser.setType("register");
                    if (storeCoupon.getUseType() > 1) {
                        storeCouponUser.setPrimaryKey(storeCoupon.getPrimaryKey());
                    }
                    couponUserList.add(storeCouponUser);
                });
            }

            // 赠送客户优惠券
            if (CollUtil.isNotEmpty(couponUserList)) {
                couponUserList.forEach(couponUser -> {
                    couponUser.setUid(user.getUid());

                });
                storeCouponUserService.saveBatch(couponUserList);
                couponList.forEach(coupon -> {
                    storeCouponService.deduction(coupon.getId(), 1, coupon.getIsLimited());
                });
            }

            return user;
        } catch (Exception e) {
            throw new MallException(e.getMessage());
        }
    }

    /**
     * 获取微信授权logo
     *
     * @return String;
     *  @author kepler
     * @since 2020-06-29
     */
    @Override
    public String getLogo() {
        String url = systemConfigService.getValueByKey(Constants.CONFIG_KEY_MOBILE_LOGIN_LOGO);
//        if(StringUtils.isNotBlank(url) && !url.contains("http")){
//            url = systemConfigService.getValueByKey(Constants.CONFIG_KEY_SITE_URL) + url;
//            url = url.replace("\\", "/");
//        }
        return url;
    }

    /**
     * 小程序登录
     *
     * @param code String 前端临时授权code
     * @param request RegisterThirdUserRequest 用户信息
     * @return UserSpreadOrderResponse;
     *  @author kepler
     * @since 2020-06-10
     */
    @Override
    public LoginResponse weChatAuthorizeProgramLogin(String code,
        RegisterThirdUserRequest request) {
        try {
            WeChatProgramAuthorizeLoginGetOpenIdResponse response = weChatService
                .programAuthorizeLogin(code);
            System.out.println("小程序登陆成功 = " + JSON.toJSONString(response));
            User user = programLogin(response.getOpenId(), response.getUnionId(), request);
            //通过用户id获取登录token信息
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(userService.token(user));
            user.setPwd(null);
            loginResponse.setUser(user);

            // 记录最后一次登录时间
            user.setLastLoginTime(DateUtil.nowDateTime());
            updateById(user);
            return loginResponse;
        } catch (Exception e) {
            throw new MallException(e.getMessage());
        }
    }

    /**
     * 推广人排行榜
     *
     * @param type String 时间范围
     * @param pageParamRequest PageParamRequest 分页
     * @return List<LoginResponse>
     *  @author kepler
     * @since 2020-05-25
     */
    @Override
    public List<User> getTopSpreadPeopleListByDate(String type, PageParamRequest pageParamRequest) {
        return userService.getTopSpreadPeopleListByDate(type, pageParamRequest);
    }

    /**
     * 佣金排行榜
     *
     * @param type String 时间范围
     * @param pageParamRequest PageParamRequest 分页
     * @return List<User>
     */
    @Override
    public List<User> getTopBrokerageListByDate(String type, PageParamRequest pageParamRequest) {
        // 获取佣金排行榜（周、月）
        List<UserBrokerageRecord> recordList = userBrokerageRecordService
            .getBrokerageTopByDate(type, pageParamRequest);
        if (CollUtil.isEmpty(recordList)) {
            return null;
        }

        List<Integer> uidList = recordList.stream().map(UserBrokerageRecord::getUid)
            .collect(Collectors.toList());
        //查询用户
        HashMap<Integer, User> userVoList = userService.getMapListInUid(uidList);

        //解决排序问题
        List<User> userList = CollUtil.newArrayList();
        for (UserBrokerageRecord record : recordList) {
            User user = new User();
            User userVo = userVoList.get(record.getUid());

            user.setUid(record.getUid());
            user.setAvatar(userVo.getAvatar());
            user.setBrokeragePrice(record.getPrice());
            if (StrUtil.isBlank(userVo.getNickname())) {
                user.setNickname(
                    userVo.getPhone().substring(0, 2) + "****" + userVo.getPhone().substring(7));
            } else {
                user.setNickname(userVo.getNickname());
            }
            userList.add(user);
        }
        return userList;
    }

    /**
     * 推广海报图
     *
     * @return List<SystemGroupData>
     *  @author kepler
     * @since 2020-06-10
     */
    @Override
    public List<UserSpreadBannerResponse> getSpreadBannerList(PageParamRequest pageParamRequest) {
        return systemGroupDataService.getListByGid(Constants.GROUP_DATA_ID_SPREAD_BANNER_LIST,
            UserSpreadBannerResponse.class);
    }

    /**
     * 当前用户在佣金排行第几名
     *
     * @param type String 时间范围
     * @return 优惠券集合
     */
    @Override
    public Integer getNumberByTop(String type) {
        int number = 0;
        Integer userId = userService.getUserIdException();
        PageParamRequest pageParamRequest = new PageParamRequest();
        pageParamRequest.setLimit(100);

        List<UserBrokerageRecord> recordList = userBrokerageRecordService
            .getBrokerageTopByDate(type, pageParamRequest);
        if (CollUtil.isEmpty(recordList)) {
            return number;
        }

        for (int i = 0; i < recordList.size(); i++) {
            if (recordList.get(i).getUid().equals(userId)) {
                number = i + 1;
                break;
            }
        }
        return number;
    }

    /**
     * 佣金转入余额
     *
     * @return Boolean
     *  @author kepler
     * @since 2020-05-18
     */
    @Override
    public Boolean transferIn(BigDecimal price) {
        //当前可提现佣金
        User user = userService.getInfo();
        if (ObjectUtil.isNull(user)) {
            throw new MallException("用户数据异常");
        }
        BigDecimal subtract = user.getBrokeragePrice();

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new MallException("转入金额不能为0");
        }
        if (subtract.compareTo(price) < 0) {
            throw new MallException("您当前可充值余额为 " + subtract + "元");
        }
        // userBill现金增加记录
        UserBill userBill = new UserBill();
        userBill.setUid(user.getUid());
        userBill.setLinkId("0");
        userBill.setPm(1);
        userBill.setTitle("佣金转余额");
        userBill.setCategory(Constants.USER_BILL_CATEGORY_MONEY);
        userBill.setType(Constants.USER_BILL_TYPE_TRANSFER_IN);
        userBill.setNumber(price);
        userBill.setBalance(user.getNowMoney().add(price));
        userBill.setMark(StrUtil.format("佣金转余额,增加{}", price));
        userBill.setStatus(1);
        userBill.setCreateTime(DateUtil.nowDateTime());

        // userBrokerage转出记录
        UserBrokerageRecord brokerageRecord = new UserBrokerageRecord();
        brokerageRecord.setUid(user.getUid());
        brokerageRecord.setLinkId("0");
        brokerageRecord.setLinkType(BrokerageRecordConstants.BROKERAGE_RECORD_LINK_TYPE_YUE);
        brokerageRecord.setType(BrokerageRecordConstants.BROKERAGE_RECORD_TYPE_SUB);
        brokerageRecord.setTitle(BrokerageRecordConstants.BROKERAGE_RECORD_TITLE_BROKERAGE_YUE);
        brokerageRecord.setPrice(price);
        brokerageRecord.setBalance(user.getNowMoney().add(price));
        brokerageRecord.setMark(StrUtil.format("佣金转余额，减少{}", price));
        brokerageRecord.setStatus(BrokerageRecordConstants.BROKERAGE_RECORD_STATUS_COMPLETE);
        brokerageRecord.setCreateTime(DateUtil.nowDateTime());

        Boolean execute = transactionTemplate.execute(e -> {
            // 扣佣金
            userService.operationBrokerage(user.getUid(), price, user.getBrokeragePrice(), "sub");
            // 加余额
            userService.operationNowMoney(user.getUid(), price, user.getNowMoney(), "add");
            userBillService.save(userBill);
            userBrokerageRecordService.save(brokerageRecord);
            return Boolean.TRUE;
        });
        return execute;
    }

    /**
     * 小程序登录
     *
     * @param openId String 微信公众号通过code取回的openId对象
     * @param unionId String unionId
     * @return List<LoginResponse>
     *  @author kepler
     * @since 2020-05-25
     */
    private User programLogin(String openId, String unionId, RegisterThirdUserRequest request) {
        try {
            //检测是否存在
            UserToken userToken = userTokenService
                .checkToken(openId, Constants.THIRD_LOGIN_TOKEN_TYPE_PROGRAM);
            if (null != userToken) {
                User user = userService.getById(userToken.getUid());
                // 分销绑定
                if (userService.checkBingSpread(user, request.getSpreadPid(), "old")) {
                    user.setSpreadUid(request.getSpreadPid());
                    user.setSpreadTime(DateUtil.nowDateTime());
                    Boolean execute = transactionTemplate.execute(e -> {
                        userService.updateById(user);
                        userService.updateSpreadCountByUid(request.getSpreadPid());
                        return Boolean.TRUE;
                    });
                    if (!execute) {
                        System.out.println(StrUtil
                            .format("公众号登录绑定分销关系失败，uid={},spreadUid={}", user.getUid(),
                                request.getSpreadPid()));
                    }
                }
                return user;
            }

            if (StringUtils.isNotBlank(unionId)) {
                userToken = userTokenService
                    .checkToken(unionId, Constants.THIRD_LOGIN_TOKEN_TYPE_PROGRAM);
                if (null != userToken) {
                    User user = userService.getById(userToken.getUid());
                    // 分销绑定
                    if (userService.checkBingSpread(user, request.getSpreadPid(), "old")) {
                        user.setSpreadUid(request.getSpreadPid());
                        user.setSpreadTime(DateUtil.nowDateTime());
                        Boolean execute = transactionTemplate.execute(e -> {
                            userService.updateById(user);
                            userService.updateSpreadCountByUid(request.getSpreadPid());
                            return Boolean.TRUE;
                        });
                        if (!execute) {
                            System.out.println(StrUtil
                                .format("公众号登录绑定分销关系失败，uid={},spreadUid={}", user.getUid(),
                                    request.getSpreadPid()));
                        }
                    }
                    return user;
                }
            }

            //TODO 是否需要强制注册用户，1 强制注册，2 需要返回数据给前端，让其输入手机号和验证码
            User user = userService.registerByThird(request, Constants.USER_LOGIN_TYPE_PROGRAM);

            userTokenService.bind(openId, Constants.THIRD_LOGIN_TOKEN_TYPE_PROGRAM, user.getUid());
            if (StringUtils.isNotBlank(unionId)) {
                //有就绑定
                userTokenService
                    .bind(unionId, Constants.THIRD_LOGIN_TOKEN_TYPE_UNION_ID, user.getUid());
            }
            // 分销绑定
            if (userService.checkBingSpread(user, request.getSpreadPid(), "new")) {
                user.setSpreadUid(request.getSpreadPid());
                user.setSpreadTime(DateUtil.nowDateTime());
                Boolean execute = transactionTemplate.execute(e -> {
                    userService.updateById(user);
                    userService.updateSpreadCountByUid(request.getSpreadPid());
                    return Boolean.TRUE;
                });
                if (!execute) {
                    System.out.println(StrUtil
                        .format("公众号登录绑定分销关系失败，uid={},spreadUid={}", user.getUid(),
                            request.getSpreadPid()));
                }
            }

            // 赠送优惠券
            // 查询是否有新人注册赠送优惠券
            List<StoreCouponUser> couponUserList = CollUtil.newArrayList();
            List<StoreCoupon> couponList = storeCouponService.findRegisterList();
            if (CollUtil.isNotEmpty(couponList)) {
                couponList.forEach(storeCoupon -> {
                    StoreCouponUser storeCouponUser = new StoreCouponUser();
                    storeCouponUser.setCouponId(storeCoupon.getId());
//                storeCouponUser.setUid(userId);
                    storeCouponUser.setName(storeCoupon.getName());
                    storeCouponUser.setMoney(storeCoupon.getMoney());
                    storeCouponUser.setMinPrice(storeCoupon.getMinPrice());
                    storeCouponUser.setStartTime(storeCoupon.getUseStartTime());
                    storeCouponUser.setEndTime(storeCoupon.getUseEndTime());
                    storeCouponUser.setUseType(storeCoupon.getUseType());
                    storeCouponUser.setType("register");
                    if (storeCoupon.getUseType() > 1) {
                        storeCouponUser.setPrimaryKey(storeCoupon.getPrimaryKey());
                    }
                    couponUserList.add(storeCouponUser);
                });
            }

            // 赠送客户优惠券
            if (CollUtil.isNotEmpty(couponUserList)) {
                couponUserList.forEach(couponUser -> {
                    couponUser.setUid(user.getUid());

                });
                storeCouponUserService.saveBatch(couponUserList);
                couponList.forEach(coupon -> {
                    storeCouponService.deduction(coupon.getId(), 1, coupon.getIsLimited());
                });
            }

            return user;
        } catch (Exception e) {
            throw new MallException(e.getMessage());
        }
    }

    /**
     * 提现记录
     *
     * @author keplerHZE
     * @since 2020-10-27
     */
    @Override
    public PageInfo<UserExtractRecordResponse> getExtractRecord(PageParamRequest pageParamRequest) {
        return userExtractService
            .getExtractRecord(userService.getUserIdException(), pageParamRequest);
    }

    /**
     * 提现总金额
     *
     * @author keplerHZE
     * @since 2020-10-27
     */
    @Override
    public BigDecimal getExtractTotalMoney() {
        return userExtractService.getExtractTotalMoney(userService.getUserIdException());
    }

    /**
     * 推广佣金明细
     *
     * @param pageParamRequest 分页参数
     */
    @Override
    public PageInfo<SpreadCommissionDetailResponse> getSpreadCommissionDetail(
        PageParamRequest pageParamRequest) {
        User user = userService.getInfo();
        if (ObjectUtil.isNull(user)) {
            throw new MallException("用户数据异常");
        }

        return userBrokerageRecordService.findDetailListByUid(user.getUid(), pageParamRequest);
    }

    /**
     * 用户账单记录（现金）
     *
     * @param type 记录类型：all-全部，expenditure-支出，income-收入
     * @return CommonPage
     */
    @Override
    public CommonPage<UserRechargeBillRecordResponse> nowMoneyBillRecord(String type,
        PageParamRequest pageRequest) {
        User user = userService.getInfo();
        if (ObjectUtil.isNull(user)) {
            throw new MallException("用户数据异常");
        }
        PageInfo<UserBill> billPageInfo = userBillService
            .nowMoneyBillRecord(user.getUid(), type, pageRequest);
        List<UserBill> list = billPageInfo.getList();

        // 获取年-月
        Map<String, List<UserBill>> map = CollUtil.newHashMap();
        list.forEach(i -> {
            String month = StrUtil
                .subPre(DateUtil.dateToStr(i.getCreateTime(), Constants.DATE_FORMAT), 7);
            if (map.containsKey(month)) {
                map.get(month).add(i);
            } else {
                List<UserBill> billList = CollUtil.newArrayList();
                billList.add(i);
                map.put(month, billList);
            }
        });
        List<UserRechargeBillRecordResponse> responseList = CollUtil.newArrayList();
        map.forEach((key, value) -> {
            UserRechargeBillRecordResponse response = new UserRechargeBillRecordResponse();
            response.setDate(key);
            response.setList(value);
            responseList.add(response);
        });

        PageInfo<UserRechargeBillRecordResponse> pageInfo = CommonPage
            .copyPageInfo(billPageInfo, responseList);
        return CommonPage.restPage(pageInfo);
    }
}
