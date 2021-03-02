package com.jjsj.mall.finance.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjsj.common.CommonPage;
import com.jjsj.common.PageParamRequest;
import com.jjsj.constants.BrokerageRecordConstants;
import com.jjsj.constants.Constants;
import com.jjsj.exception.MallException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jjsj.mall.finance.response.BalanceResponse;
import com.jjsj.mall.finance.response.UserExtractResponse;
import com.jjsj.utils.DateUtil;
import com.jjsj.utils.vo.dateLimitUtilVo;
import com.jjsj.mall.finance.dao.UserExtractDao;
import com.jjsj.mall.finance.model.UserExtract;
import com.jjsj.mall.finance.request.UserExtractRequest;
import com.jjsj.mall.finance.request.UserExtractSearchRequest;
import com.jjsj.mall.finance.service.UserExtractService;
import com.jjsj.mall.front.response.UserExtractRecordResponse;
import com.jjsj.mall.system.service.SystemAttachmentService;
import com.jjsj.mall.system.service.SystemConfigService;
import com.jjsj.mall.user.model.User;
import com.jjsj.mall.user.model.UserBrokerageRecord;
import com.jjsj.mall.user.service.UserBillService;
import com.jjsj.mall.user.service.UserBrokerageRecordService;
import com.jjsj.mall.user.service.UserService;
import com.jjsj.mall.wechat.service.impl.WechatSendMessageForMinService;
import com.jjsj.mall.wechat.vo.WechatSendMessageForCash;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;

/**
*  UserExtractServiceImpl 接口实现

*/
@Service
public class UserExtractServiceImpl extends ServiceImpl<UserExtractDao, UserExtract> implements UserExtractService {

    @Resource
    private UserExtractDao dao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserBillService userBillService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private WechatSendMessageForMinService wechatSendMessageForMinService;

    @Autowired
    private SystemAttachmentService systemAttachmentService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private UserBrokerageRecordService userBrokerageRecordService;


    /**
    * 列表
    * @param request 请求参数
    * @param pageParamRequest 分页类参数
    *  @author kepler
    * @since 2020-05-11
    * @return List<UserExtract>
    */
    @Override
    public List<UserExtract> getList(UserExtractSearchRequest request, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());

        //带 UserExtract 类的多条件查询
        LambdaQueryWrapper<UserExtract> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isBlank(request.getKeywords())){
            lambdaQueryWrapper.and(i -> i.
                    or().like(UserExtract::getWechat, request.getKeywords()).   //微信号
                    or().like(UserExtract::getRealName, request.getKeywords()). //名称
                    or().like(UserExtract::getBankCode, request.getKeywords()). //银行卡
                    or().like(UserExtract::getBankAddress, request.getKeywords()). //开户行
                    or().like(UserExtract::getAlipayCode, request.getKeywords()). //支付宝
                    or().like(UserExtract::getFailMsg, request.getKeywords()) //失败原因
            );
        }

        //提现状态
        if(request.getStatus() != null){
            lambdaQueryWrapper.eq(UserExtract::getStatus, request.getStatus());
        }

        //提现方式
        if(!StringUtils.isBlank(request.getExtractType())){
            lambdaQueryWrapper.eq(UserExtract::getExtractType, request.getExtractType());
        }

        //时间范围
        if(StringUtils.isNotBlank(request.getDateLimit())){
            dateLimitUtilVo dateLimit = DateUtil.getDateLimit(request.getDateLimit());
            lambdaQueryWrapper.between(UserExtract::getCreateTime, dateLimit.getStartTime(), dateLimit.getEndTime());
        }

        //按创建时间降序排列
        lambdaQueryWrapper.orderByDesc(UserExtract::getCreateTime, UserExtract::getId);

        List<UserExtract> extractList = dao.selectList(lambdaQueryWrapper);
        if (CollUtil.isEmpty(extractList)) {
            return extractList;
        }
        List<Integer> uidList = extractList.stream().map(o -> o.getUid()).distinct().collect(Collectors.toList());
        HashMap<Integer, User> userMap = userService.getMapListInUid(uidList);
        for (UserExtract userExtract : extractList) {
            userExtract.setNickName(Optional.ofNullable(userMap.get(userExtract.getUid()).getNickname()).orElse(""));
        }
        return extractList;
    }

    /**
     * 提现总金额
     * 总佣金 = 已提现佣金 + 未提现佣金
     * 已提现佣金 = 用户成功提现的金额
     * 未提现佣金 = 用户未提现的佣金 = 可提现佣金 + 冻结佣金 = 用户佣金
     * 可提现佣金 = 包括解冻佣金、提现未通过的佣金 = 用户佣金 - 冻结期佣金
     * 待提现佣金 = 待审核状态的佣金
     * 冻结佣金 = 用户在冻结期的佣金，不包括退回佣金
     * 退回佣金 = 因退款导致的冻结佣金退回
     */
    @Override
    public BalanceResponse getBalance(String dateLimit) {
        String startTime = "";
        String endTime = "";
        if(StringUtils.isNotBlank(dateLimit)){
            dateLimitUtilVo dateRage = DateUtil.getDateLimit(dateLimit);
            startTime = dateRage.getStartTime();
            endTime = dateRage.getEndTime();
        }

        // 已提现
        BigDecimal withdrawn = getWithdrawn(startTime, endTime);
        // 待提现(审核中)
        BigDecimal toBeWithdrawn = getWithdrawning(startTime, endTime);

        // 佣金总金额（单位时间）
        BigDecimal commissionTotal = userBrokerageRecordService.getTotalSpreadPriceBydateLimit(dateLimit);
        // 单位时间消耗的佣金
        BigDecimal subWithdarw = userBrokerageRecordService.getSubSpreadPriceByDateLimit(dateLimit);
        // 未提现
        BigDecimal unDrawn = commissionTotal.subtract(subWithdarw);
        return new BalanceResponse(withdrawn, unDrawn, commissionTotal, toBeWithdrawn);
    }


    /**
     * 提现总金额
     *  @author kepler
     * @since 2020-05-11
     * @return BalanceResponse
     */
    @Override
    public BigDecimal getWithdrawn(String startTime, String endTime) {
        return getSum(null, 1, startTime, endTime);
    }

    /**
     * 审核中总金额
     *  @author kepler
     * @since 2020-05-11
     * @return BalanceResponse
     */
    @Override
    public BigDecimal getWithdrawning(String startTime, String endTime) {
        return getSum(null, 0, startTime, endTime);
    }

    /**
     * 获取待提现总金额
     *
     * @return 待提现总金额
     */
    @Override
    public BigDecimal getWaiteForDrawn(String startTime,String endTime) {
        return getSum(null,-1,startTime,endTime);
    }

    /**
     * 提现申请
     *  @author kepler
     * @since 2020-06-08
     * @return Boolean
     */
    @Override
    public Boolean create(UserExtractRequest request, Integer userId) {
        //添加判断，提现金额不能小于10元
        BigDecimal ten = new BigDecimal(10);
        if (request.getExtractPrice().compareTo(ten) < 0) {
            throw new MallException("最低提现金额10元");
        }
        //看是否有足够的金额可提现
        User user = userService.getById(userId);
        BigDecimal toBeWithdrawn = user.getBrokeragePrice();//提现总金额
        BigDecimal freeze = getFreeze(userId); //冻结的佣金
        BigDecimal money = toBeWithdrawn.subtract(freeze); //可提现总金额

        if(money.compareTo(ZERO) < 1){
            throw new MallException("您当前没有金额可以提现");
        }

        int result = money.compareTo(request.getExtractPrice());
        if(result < 0){
            throw new MallException("你当前最多可提现 " + toBeWithdrawn + "元");
        }
        UserExtract userExtract = new UserExtract();
        userExtract.setUid(userId);
        BeanUtils.copyProperties(request, userExtract);
        userExtract.setBalance(toBeWithdrawn.subtract(request.getExtractPrice()));
        //存入银行名称
//        userExtract.setBankName(request.getBankName());
        if (StrUtil.isNotBlank(userExtract.getQrcodeUrl())) {
            userExtract.setQrcodeUrl(systemAttachmentService.clearPrefix(userExtract.getQrcodeUrl()));
        }

        // 微信小程序订阅提现通知
        WechatSendMessageForCash cash = new WechatSendMessageForCash(
                "提现申请成功",request.getExtractPrice()+"",request.getBankName()+request.getBankCode(),
                DateUtil.nowDateTimeStr(),"暂无",request.getRealName(),"0",request.getExtractType(),"提现",
                "暂无",request.getExtractType(),"暂无",request.getRealName()
        );
        wechatSendMessageForMinService.sendCashMessage(cash,userId);
//        return save(userExtract);
        save(userExtract);
        // 扣除用户总金额
        return userService.upadteBrokeragePrice(user, toBeWithdrawn.subtract(request.getExtractPrice()));
    }


//    /**
//     * 可提现总金额
//     *  @author kepler
//     * @since 2020-06-08
//     * @return Boolean
//     */
//    @Override
//    public BigDecimal getToBeWihDraw(Integer userId) {
//        //可提现佣金
//        //返佣 +
//        BigDecimal withDrawable = userBillService.getSumBigDecimal(1, userId, Constants.USER_BILL_CATEGORY_MONEY, Constants.SEARCH_DATE_LATELY_30, Constants.USER_BILL_TYPE_BROKERAGE);
//
//        //退款退的佣金 -
//        BigDecimal refund = userBillService.getSumBigDecimal(0, userId, Constants.USER_BILL_CATEGORY_MONEY, Constants.SEARCH_DATE_LATELY_30, Constants.USER_BILL_TYPE_BROKERAGE);
//
//        BigDecimal subtract = withDrawable.subtract(refund);
//        subtract = (subtract.compareTo(ZERO) < 1) ? ZERO : subtract;
//
//        //用户累计佣金
//        BigDecimal brokeragePrice = userService.getById(userId).getBrokeragePrice();
//
//        //可提现佣金
//        return brokeragePrice.subtract(subtract);
//    }

    /**
     * 冻结的佣金
     *  @author kepler
     * @since 2020-06-08
     * @return Boolean
     */
    @Override
    public BigDecimal getFreeze(Integer userId) {
//        //冻结时间
//        //查看是否在冻结期之内， 如果在是需要回滚的，如果不在则不需要回滚
//        String time = systemConfigService.getValueByKey(Constants.CONFIG_KEY_STORE_BROKERAGE_EXTRACT_TIME);
//
//        String date = null;
//        if(StringUtils.isNotBlank(time)){
//            String startTime = DateUtil.nowDateTime(Constants.DATE_FORMAT);
//            String endTime = DateUtil.addDay(DateUtil.nowDateTime(), Integer.parseInt(time), Constants.DATE_FORMAT);
//            date = startTime + "," + endTime;
//        }
//
//        //在此期间获得的佣金
//        BigDecimal getSum = userBillService.getSumBigDecimal(1, userId, Constants.USER_BILL_CATEGORY_BROKERAGE_PRICE, null, date);
//
//        //在此期间消耗的佣金
//        BigDecimal subSum = userBillService.getSumBigDecimal(0, userId, Constants.USER_BILL_CATEGORY_BROKERAGE_PRICE, null, date);
//
//        //冻结的佣金
//        return getSum.subtract(subSum);
        String time = systemConfigService.getValueByKey(Constants.CONFIG_KEY_STORE_BROKERAGE_EXTRACT_TIME);
        if (StrUtil.isBlank(time)) {
            return BigDecimal.ZERO;
        }
        String endTime = DateUtil.nowDateTime(Constants.DATE_FORMAT);
        String startTime = DateUtil.addDay(DateUtil.nowDateTime(), -Integer.parseInt(time), Constants.DATE_FORMAT);
        String date = startTime + "," + endTime;
        //在冻结期的资金
        BigDecimal getSum = userBillService.getSumBigDecimal(1, userId, Constants.USER_BILL_CATEGORY_BROKERAGE_PRICE, date, null);
        return getSum;
    }

    /**
     * 根据状态获取总额
     * @return BigDecimal
     */
    private BigDecimal getSum(Integer userId, int status, String startTime, String endTime) {
        LambdaQueryWrapper<UserExtract> lqw = Wrappers.lambdaQuery();
        if(null != userId) {
            lqw.eq(UserExtract::getUid,userId);
        }
        lqw.eq(UserExtract::getStatus,status);
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            lqw.between(UserExtract::getCreateTime, startTime, endTime);
        }
        List<UserExtract> userExtracts = dao.selectList(lqw);
        BigDecimal sum = ZERO;
        if(CollUtil.isNotEmpty(userExtracts)) {
            sum = userExtracts.stream().map(UserExtract::getExtractPrice).reduce(ZERO, BigDecimal::add);
        }
        return sum;
    }

    /**
     * 获取用户对应的提现数据
     * @param userId 用户id
     * @return 提现数据
     */
    @Override
    public UserExtractResponse getUserExtractByUserId(Integer userId) {
        QueryWrapper<UserExtract> qw = new QueryWrapper<>();
        qw.select("SUM(extract_price) as extract_price,count(id) as id, uid");
        qw.ge("status", 1);
        qw.eq("uid",userId);
        qw.groupBy("uid");
        UserExtract ux = dao.selectOne(qw);
        UserExtractResponse uexr = new UserExtractResponse();
//        uexr.setEuid(ux.getUid());
        if(null != ux){
            uexr.setExtractCountNum(ux.getId()); // 这里的id其实是数量，借变量传递
            uexr.setExtractCountPrice(ux.getExtractPrice());
        }else{
            uexr.setExtractCountNum(0); // 这里的id其实是数量，借变量传递
            uexr.setExtractCountPrice(ZERO);
        }

        return uexr;
    }

    /**
     * 根据用户id集合获取对应提现用户集合
     * @param userIds 用户id集合
     * @return 提现用户集合
     */
    @Override
    public List<UserExtract> getListByUserIds(List<Integer> userIds) {
        LambdaQueryWrapper<UserExtract> lqw = new LambdaQueryWrapper<>();
        lqw.in(UserExtract::getUid, userIds);
        return dao.selectList(lqw);
    }

    /**
     * 提现审核
     *
     * @param id          提现申请id
     * @param status      审核状态 -1 未通过 0 审核中 1 已提现
     * @param backMessage 驳回原因
     * @return 审核结果
     */
    @Override
    public Boolean updateStatus(Integer id, Integer status, String backMessage) {
        if(status == -1 && StringUtils.isBlank(backMessage))
            throw new MallException("驳回时请填写驳回原因");

        UserExtract userExtract = getById(id);
        if (ObjectUtil.isNull(userExtract)) {
            throw new MallException("提现申请记录不存在");
        }
        if (userExtract.getStatus() != 0) {
            throw new MallException("提现申请已处理过");
        }
        userExtract.setStatus(status);

        User user = userService.getById(userExtract.getUid());
        if (ObjectUtil.isNull(user)) {
            throw new MallException("提现用户数据异常");
        }

        Boolean execute = false;

        // 拒绝
        if (status == -1) {//未通过时恢复用户总金额
            userExtract.setFailMsg(backMessage);
            // 添加提现申请拒绝佣金记录
            UserBrokerageRecord brokerageRecord = new UserBrokerageRecord();
            brokerageRecord.setUid(user.getUid());
            brokerageRecord.setLinkId(userExtract.getId().toString());
            brokerageRecord.setLinkType(BrokerageRecordConstants.BROKERAGE_RECORD_LINK_TYPE_WITHDRAW);
            brokerageRecord.setType(BrokerageRecordConstants.BROKERAGE_RECORD_TYPE_ADD);
            brokerageRecord.setTitle(BrokerageRecordConstants.BROKERAGE_RECORD_TITLE_WITHDRAW_FAIL);
            brokerageRecord.setPrice(userExtract.getExtractPrice());
            brokerageRecord.setBalance(user.getBrokeragePrice().add(userExtract.getExtractPrice()));
            brokerageRecord.setMark(StrUtil.format("提现申请拒绝返还佣金{}", userExtract.getExtractPrice()));
            brokerageRecord.setStatus(BrokerageRecordConstants.BROKERAGE_RECORD_STATUS_COMPLETE);
            brokerageRecord.setCreateTime(DateUtil.nowDateTime());

            execute = transactionTemplate.execute(e -> {
                // 返还佣金
                userService.operationBrokerage(userExtract.getUid(), userExtract.getExtractPrice(), user.getBrokeragePrice(), "add");
                updateById(userExtract);
                userBrokerageRecordService.save(brokerageRecord);
                return Boolean.TRUE;
            });
        }

        // 同意
        if (status == 1) {
            // 获取佣金提现记录
            UserBrokerageRecord brokerageRecord = userBrokerageRecordService.getByLinkIdAndLinkType(userExtract.getId().toString(), BrokerageRecordConstants.BROKERAGE_RECORD_LINK_TYPE_WITHDRAW);
            if (ObjectUtil.isNull(brokerageRecord)) {
                throw new MallException("对应的佣金记录不存在");
            }
            execute = transactionTemplate.execute(e -> {
                updateById(userExtract);
                brokerageRecord.setStatus(BrokerageRecordConstants.BROKERAGE_RECORD_STATUS_COMPLETE);
                userBrokerageRecordService.updateById(brokerageRecord);
                return Boolean.TRUE;
            });
        }
        return execute;
    }

    /**
     * 提现记录
     * @return
     */
    @Override
    public PageInfo<UserExtractRecordResponse> getExtractRecord(Integer userId, PageParamRequest pageParamRequest){
//        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        Page<UserExtract> userExtractPage = PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        QueryWrapper<UserExtract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", userId);

        queryWrapper.groupBy("left(create_time, 7)");
        queryWrapper.orderByDesc("left(create_time, 7)");
        List<UserExtract> list = dao.selectList(queryWrapper);
        if(CollUtil.isEmpty(list)){
            return new PageInfo<>();
        }
        ArrayList<UserExtractRecordResponse> userExtractRecordResponseList = CollectionUtil.newArrayList();
        for (UserExtract userExtract : list) {
            String date = DateUtil.dateToStr(userExtract.getCreateTime(), Constants.DATE_FORMAT_MONTH);
            userExtractRecordResponseList.add(new UserExtractRecordResponse(date, getListByMonth(userId, date)));
        }

        return CommonPage.copyPageInfo(userExtractPage, userExtractRecordResponseList);
    }

    private List<UserExtract> getListByMonth(Integer userId, String date) {
        QueryWrapper<UserExtract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", userId);
        queryWrapper.apply(StrUtil.format(" left(create_time, 7) = '{}'", date));
        queryWrapper.orderByDesc("create_time");
        return dao.selectList(queryWrapper);
    }

    /**
     * 获取用户提现总金额
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getExtractTotalMoney(Integer userId){
        return getSum(userId, 1, null, null);
    }


    /**
     * 提现申请
     * @return
     */
    @Override
    public Boolean extractApply(UserExtractRequest request) {
        //添加判断，提现金额不能后台配置金额
        String value = systemConfigService.getValueByKeyException(Constants.CONFIG_EXTRACT_MIN_PRICE);
        BigDecimal ten = new BigDecimal(value);
        if (request.getExtractPrice().compareTo(ten) < 0) {
            throw new MallException(StrUtil.format("最低提现金额{}元", ten));
        }

        User user = userService.getInfo();
        if (ObjectUtil.isNull(user)) {
            throw new MallException("提现用户信息异常");
        }
        BigDecimal money = user.getBrokeragePrice();//可提现总金额
        if(money.compareTo(ZERO) < 1){
            throw new MallException("您当前没有金额可以提现");
        }

        if(money.compareTo(request.getExtractPrice()) < 0){
            throw new MallException("你当前最多可提现 " + money + "元");
        }

        UserExtract userExtract = new UserExtract();
        BeanUtils.copyProperties(request, userExtract);
        userExtract.setUid(user.getUid());
        userExtract.setBalance(money.subtract(request.getExtractPrice()));
        //存入银行名称
        if (StrUtil.isNotBlank(userExtract.getQrcodeUrl())) {
            userExtract.setQrcodeUrl(systemAttachmentService.clearPrefix(userExtract.getQrcodeUrl()));
        }

        // 添加佣金记录
        UserBrokerageRecord brokerageRecord = new UserBrokerageRecord();
        brokerageRecord.setUid(user.getUid());
        brokerageRecord.setLinkType(BrokerageRecordConstants.BROKERAGE_RECORD_LINK_TYPE_WITHDRAW);
        brokerageRecord.setType(BrokerageRecordConstants.BROKERAGE_RECORD_TYPE_SUB);
        brokerageRecord.setTitle(BrokerageRecordConstants.BROKERAGE_RECORD_TITLE_WITHDRAW_APPLY);
        brokerageRecord.setPrice(userExtract.getExtractPrice());
        brokerageRecord.setBalance(money.subtract(userExtract.getExtractPrice()));
        brokerageRecord.setMark(StrUtil.format("提现申请扣除佣金{}", userExtract.getExtractPrice()));
        brokerageRecord.setStatus(BrokerageRecordConstants.BROKERAGE_RECORD_STATUS_WITHDRAW);
        brokerageRecord.setCreateTime(DateUtil.nowDateTime());

        Boolean execute = transactionTemplate.execute(e -> {
            // 保存提现记录
            save(userExtract);
            // 修改用户佣金
            userService.operationBrokerage(user.getUid(), userExtract.getExtractPrice(), money, "sub");
            // 添加佣金记录
            brokerageRecord.setLinkId(userExtract.getId().toString());
            userBrokerageRecordService.save(brokerageRecord);
            return Boolean.TRUE;
        });

        //todo 提现申请通知
        return execute;
    }
}

