package com.jjsj.mall.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjsj.common.PageParamRequest;
import com.jjsj.constants.Constants;
import com.jjsj.exception.MallException;
import com.github.pagehelper.PageHelper;
import com.jjsj.mall.front.request.UserSignInfoRequest;
import com.jjsj.mall.front.response.UserSignInfoResponse;
import com.jjsj.mall.system.service.SystemGroupDataService;
import com.jjsj.mall.system.vo.SystemGroupDataSignConfigVo;
import com.jjsj.mall.wechat.service.impl.WechatSendMessageForMinService;
import com.jjsj.mall.wechat.vo.WechatSendMessageForIntegral;
import com.jjsj.utils.DateUtil;
import com.jjsj.mall.user.dao.UserSignDao;
import com.jjsj.mall.user.model.User;
import com.jjsj.mall.user.model.UserSign;
import com.jjsj.mall.user.request.UserOperateFundsRequest;
import com.jjsj.mall.user.service.UserBillService;
import com.jjsj.mall.user.service.UserService;
import com.jjsj.mall.user.service.UserSignService;
import com.jjsj.mall.user.vo.UserSignMonthVo;
import com.jjsj.mall.user.vo.UserSignVo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * UserSignServiceImpl 接口实现
 */
@Service
public class UserSignServiceImpl extends ServiceImpl<UserSignDao, UserSign> implements
    UserSignService {

    @Resource
    private UserSignDao dao;

    @Autowired
    private UserService userService;

    @Autowired
    private SystemGroupDataService systemGroupDataService;

    @Autowired
    private UserBillService userBillService;

    @Autowired
    private WechatSendMessageForMinService wechatSendMessageForMinService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 列表
     *
     * @param pageParamRequest 分页类参数
     * @return List<UserSignVo>
     *  @author kepler
     * @since 2020-04-30
     */
    @Override
    public List<UserSignVo> getList(PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<UserSign> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserSign::getType, 1);
        lambdaQueryWrapper.eq(UserSign::getUid, userService.getUserIdException());
        lambdaQueryWrapper.orderByDesc(UserSign::getId);
        List<UserSign> userSignList = dao.selectList(lambdaQueryWrapper);

        ArrayList<UserSignVo> userSignVoList = new ArrayList<>();
        if (userSignList.size() < 1) {
            return userSignVoList;
        }

        for (UserSign userSign : userSignList) {
            userSignVoList.add(
                new UserSignVo(userSign.getTitle(), userSign.getNumber(), userSign.getCreateDay()));
        }
        return userSignVoList;
    }

    /**
     * 签到
     *
     *  @author kepler
     * @since 2020-04-30
     */
    @Override
    public SystemGroupDataSignConfigVo sign() {
        User user = userService.getInfoException();
        SystemGroupDataSignConfigVo configVo = getSignInfo(user.getUid());
        if (configVo == null) {
            throw new MallException("请先配置签到天数！");
        }

        //保存签到数据
        UserSign userSign = new UserSign();
        userSign.setUid(user.getUid());
        userSign.setTitle(Constants.SIGN_TYPE_INTEGRAL_TITLE);
        userSign.setNumber(configVo.getIntegral());
        userSign.setType(Constants.SIGN_TYPE_INTEGRAL);
        userSign.setBalance(user.getIntegral().intValue() + configVo.getIntegral());
        userSign.setCreateDay(DateUtil
            .strToDate(DateUtil.nowDate(Constants.DATE_FORMAT_DATE), Constants.DATE_FORMAT_DATE));

        //更新用户积分信息
        UserOperateFundsRequest integralFundsRequest = new UserOperateFundsRequest();
        integralFundsRequest.setUid(user.getUid());
        integralFundsRequest.setFoundsCategory(Constants.USER_BILL_CATEGORY_INTEGRAL);
        integralFundsRequest.setFoundsType(Constants.USER_BILL_TYPE_SIGN);
        integralFundsRequest.setTitle(Constants.SIGN_TYPE_INTEGRAL_TITLE);
        integralFundsRequest.setType(1);
        integralFundsRequest.setValue(new BigDecimal(configVo.getIntegral()));

        //更新用户经验信息
        UserOperateFundsRequest experienceFundsRequest = new UserOperateFundsRequest();
        experienceFundsRequest.setUid(user.getUid());
        experienceFundsRequest.setFoundsCategory(Constants.USER_BILL_CATEGORY_EXPERIENCE);
        experienceFundsRequest.setFoundsType(Constants.USER_BILL_TYPE_SIGN);
        experienceFundsRequest.setTitle(Constants.SIGN_TYPE_EXPERIENCE_TITLE);
        experienceFundsRequest.setType(1);
        experienceFundsRequest.setValue(new BigDecimal(configVo.getExperience()));

        //更新用户签到天数
        user.setSignNum(user.getSignNum() + 1);
        //更新用户积分
        user.setIntegral(user.getIntegral() + integralFundsRequest.getValue().intValue());
        // 更新用户经验
        user.setExperience(user.getExperience() + experienceFundsRequest.getValue().intValue());

        Boolean execute = transactionTemplate.execute(e -> {
            //保存签到数据
            save(userSign);
            //更新用户积分信息
            userService.updateFounds(integralFundsRequest, true);
            //更新用户经验信息
            userService.updateFounds(experienceFundsRequest, true);
            //更新用户 签到天数、积分、经验
            userService.updateById(user);
            return Boolean.TRUE;
        });

        if (!execute) {
            throw new MallException("修改用户签到信息失败!");
        }
        // 小程序消息积分变动通知
        WechatSendMessageForIntegral integralPram = new WechatSendMessageForIntegral(
            "您的积分变动如下", "签到获得积分", "签到", "0", configVo.getIntegral() + "",
            (user.getIntegral() + configVo.getIntegral()) + "",
            DateUtil.nowDateTimeStr(), "暂无", "暂无", "签到赠送积分"
        );
        wechatSendMessageForMinService.sendIntegralMessage(integralPram, user.getUid());

        return configVo;
    }


    /**
     * 详情
     *
     * @return map
     *  @author kepler
     * @since 2020-04-30
     */
    @Override
    public HashMap<String, Object> get() {
        HashMap<String, Object> map = new HashMap<>();
        //当前积分
        User info = userService.getInfo();
        map.put("integral", info.getIntegral());
        //总计签到天数
        map.put("count", signCount(info.getUid()));
        //连续签到数据

        //今日是否已经签到
        map.put("today", false);
        return map;
    }

    /**
     * 签到配置
     *
     * @return List<SystemGroupDataSignConfigVo>
     *  @author kepler
     * @since 2020-05-28
     */
    @Override
    public List<SystemGroupDataSignConfigVo> config() {
        //获取配置数据
        return systemGroupDataService
            .getListByGid(Constants.GROUP_DATA_ID_SIGN, SystemGroupDataSignConfigVo.class);
    }

    /**
     * 列表年月
     *
     * @param pageParamRequest 分页类参数
     * @return List<UserSignVo>
     *  @author kepler
     * @since 2020-04-30
     */
    @Override
    public List<UserSignMonthVo> getListGroupMonth(PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<UserSign> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserSign::getType, 1);
        lambdaQueryWrapper.eq(UserSign::getUid, userService.getUserIdException());
        lambdaQueryWrapper.orderByDesc(UserSign::getCreateDay);
        List<UserSign> userSignList = dao.selectList(lambdaQueryWrapper);

        ArrayList<UserSignMonthVo> signMonthVoArrayList = new ArrayList<>();
        if (userSignList.size() < 1) {
            return signMonthVoArrayList;
        }

        for (UserSign userSign : userSignList) {
            String date = DateUtil.dateToStr(userSign.getCreateDay(), Constants.DATE_FORMAT_MONTH);
            UserSignVo userSignVo = new UserSignVo(userSign.getTitle(), userSign.getNumber(),
                userSign.getCreateDay());

            boolean findResult = false;
            if (signMonthVoArrayList.size() > 0) {
                //有数据之后则 判断是否已存在，存在则更新
                for (UserSignMonthVo userSignMonthVo : signMonthVoArrayList) {
                    if (userSignMonthVo.getMonth().equals(date)) {
                        userSignMonthVo.getList().add(userSignVo);
                        findResult = true;
                        break;
                    }
                }
            }

            //不存在则创建
            if (!findResult) {
                //如果没有找到则需要单独添加
                ArrayList<UserSignVo> userSignVoArrayList = new ArrayList<>();
                userSignVoArrayList.add(userSignVo);
                signMonthVoArrayList.add(new UserSignMonthVo(date, userSignVoArrayList));
            }
        }
        return signMonthVoArrayList;
    }

    /**
     * 查询签到记录表信息
     *
     * @return UserSignInfoResponse
     *  @author kepler
     * @since 2020-05-29
     */
    @Override
    public UserSignInfoResponse getUserInfo(UserSignInfoRequest request) {
        UserSignInfoResponse userSignInfoResponse = new UserSignInfoResponse();

        //用户信息
        User user = userService.getInfo();
        BeanUtils.copyProperties(user, userSignInfoResponse);

        //当前用户已经签到完一个周期，那么重置
        if (user.getSignNum().equals(config().size())) {
            userSignInfoResponse.setSignNum(0);
            userService.repeatSignNum(user.getUid());
        }

        //签到
        if (request.getAll() || request.getSign()) {
            userSignInfoResponse.setSumSignDay(getCount(user.getUid()));
            userSignInfoResponse.setIsDaySign(checkDaySign(user.getUid()));
            userSignInfoResponse.setIsYesterdaySign(checkYesterdaySign(user.getUid()));
        }

        //积分
        if (request.getAll() || request.getSign()) {
            userSignInfoResponse.setSumIntegral(userBillService
                .getSumInteger(1, user.getUid(), Constants.USER_BILL_CATEGORY_INTEGRAL, null,
                    null));
            userSignInfoResponse.setDeductionIntegral(userBillService
                .getSumInteger(0, user.getUid(), Constants.USER_BILL_CATEGORY_INTEGRAL, null,
                    null));
//            userSignInfoResponse.setYesterdayIntegral(userBillService.getSumInteger(1, user.getUid(), Constants.USER_BILL_CATEGORY_INTEGRAL, Constants.SEARCH_DATE_YESTERDAY, null));
            //实际上是今日获得积分
            userSignInfoResponse.setYesterdayIntegral(userBillService
                .getSumInteger(1, user.getUid(), Constants.USER_BILL_CATEGORY_INTEGRAL,
                    Constants.SEARCH_DATE_DAY, null));
        }

        return userSignInfoResponse;
    }

    /**
     * 检测今天是否签到
     *
     * @param userId Integer 用户id
     * @return UserSignInfoResponse
     *  @author kepler
     * @since 2020-05-29
     */
    private Boolean checkDaySign(Integer userId) {
        List<UserSign> userSignList = getInfoByDay(userId,
            DateUtil.nowDate(Constants.DATE_FORMAT_DATE));
        return userSignList.size() != 0;
    }

    /**
     * 检测昨天天是否签到
     *
     * @param userId Integer 用户id
     * @return UserSignInfoResponse
     *  @author kepler
     * @since 2020-05-29
     */
    private Boolean checkYesterdaySign(Integer userId) {
        String day = DateUtil.nowDate(Constants.DATE_FORMAT_DATE);
        String yesterday = DateUtil.addDay(day, -1, Constants.DATE_FORMAT_DATE);
        List<UserSign> userSignList = getInfoByDay(userId, yesterday);
        return userSignList.size() != 0;
    }

    /**
     * 根据日期查询数据
     *
     * @param userId Integer 用户id
     * @param date Date 日期
     * @return UserSignInfoResponse
     *  @author kepler
     * @since 2020-05-29
     */
    private List<UserSign> getInfoByDay(Integer userId, String date) {
        LambdaQueryWrapper<UserSign> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserSign::getUid, userId).eq(UserSign::getType, 1)
            .eq(UserSign::getCreateDay, date);
        return dao.selectList(lambdaQueryWrapper);

    }


    /**
     * 累计签到次数
     *
     * @param userId Integer 用户id
     * @return UserSignInfoResponse
     *  @author kepler
     * @since 2020-05-29
     */
    private Integer getCount(Integer userId) {
        LambdaQueryWrapper<UserSign> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserSign::getUid, userId).eq(UserSign::getType, 1);
        return dao.selectCount(lambdaQueryWrapper);
    }

    /**
     * 获取签到积分
     *
     * @param userId Integer 用户id
     * @return Integer
     *  @author kepler
     * @since 2020-04-30
     */
    private SystemGroupDataSignConfigVo getSignInfo(Integer userId) {
        //先看用户上次签到是什么日期， 如果有断开那么就重置
        checkRepeat(userId);

        //获取用户连续签到天数
        User user = userService.getInfo();

        //获取签到数据
        List<SystemGroupDataSignConfigVo> config = config();

        //如果已经签到一个周期，那么再次签到的时候直接从第一天重新开始
        if (user.getSignNum().equals(config.size())) {
            user.setSignNum(0);
            userService.repeatSignNum(userId);
        }

        for (SystemGroupDataSignConfigVo systemSignConfigVo : config) {
            if (user.getSignNum() + 1 <= systemSignConfigVo.getDay()) {
                return systemSignConfigVo;
            }
        }

        return null;
    }

    /**
     * 检测是否需要重置累计签到天数
     *
     * @param userId Integer 用户id
     * @param userId Integer 用户id
     *  @author kepler
     * @since 2020-04-30
     */
    private void checkRepeat(Integer userId) {
        PageHelper.startPage(Constants.DEFAULT_PAGE, Constants.DEFAULT_PAGE);
        LambdaQueryWrapper<UserSign> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(UserSign::getCreateDay).eq(UserSign::getUid, userId)
            .orderByDesc(UserSign::getCreateDay);
        List<UserSign> userSignList = dao.selectList(lambdaQueryWrapper);
        if (userSignList.size() < 1) {
            //没有签到过
            userService.repeatSignNum(userId);
            return;
        }

        //签到时间 +1 天
        String lastDate = DateUtil
            .dateToStr(userSignList.get(0).getCreateDay(), Constants.DATE_FORMAT_DATE);
        String nowDate = DateUtil.nowDate(Constants.DATE_FORMAT_DATE);
        String nextDate = DateUtil
            .addDay(userSignList.get(0).getCreateDay(), 1, Constants.DATE_FORMAT_DATE);

        int compareDate = DateUtil.compareDate(nextDate, nowDate, Constants.DATE_FORMAT_DATE);

        //对比今天数据
        if (DateUtil.compareDate(lastDate, nowDate, Constants.DATE_FORMAT_DATE) == 0) {
            throw new MallException("今日已签到。不可重复签到");
        }

        if (compareDate != 0) {
            //不相等，所以不是连续签到
            userService.repeatSignNum(userId);
        }
    }

    /**
     * 累计签到天数
     *
     * @param userId Integer 用户id
     * @return Integer
     *  @author kepler
     * @since 2020-04-30
     */
    private Integer signCount(Integer userId) {
        LambdaQueryWrapper<UserSign> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserSign::getUid, userId);
        return dao.selectCount(lambdaQueryWrapper);
    }

    /**
     * 条件获取列表
     */
    @Override
    public List<UserSign> getListByCondition(UserSign sign, PageParamRequest pageParamRequest) {
        LambdaQueryWrapper<UserSign> lqw = new LambdaQueryWrapper<>();
        lqw.setEntity(sign);
        lqw.orderByDesc(UserSign::getCreateTime);
        return dao.selectList(lqw);
    }
}

