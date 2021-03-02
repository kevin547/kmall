package com.jjsj.mall.payment.service.impl;

import cn.hutool.core.util.StrUtil;
import com.jjsj.constants.Constants;
import com.jjsj.exception.MallException;
import com.jjsj.utils.DateUtil;
import com.jjsj.mall.finance.model.UserRecharge;
import com.jjsj.mall.finance.service.UserRechargeService;
import com.jjsj.mall.payment.service.PayService;
import com.jjsj.mall.payment.service.RechargePayService;
import com.jjsj.mall.payment.vo.wechat.AttachVo;
import com.jjsj.mall.payment.vo.wechat.CreateOrderResponseVo;
import com.jjsj.mall.payment.vo.wechat.PayParamsVo;
import com.jjsj.mall.payment.wechat.WeChatPayService;
import com.jjsj.mall.sms.service.SmsService;
import com.jjsj.mall.user.model.User;
import com.jjsj.mall.user.model.UserBill;
import com.jjsj.mall.user.request.UserOperateFundsRequest;
import com.jjsj.mall.user.service.UserBillService;
import com.jjsj.mall.user.service.UserService;
import com.jjsj.mall.wechat.service.TemplateMessageService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.HashMap;


/**
 * 支付类

 */
@EqualsAndHashCode(callSuper = true)
@Data
@Service
public class RechargePayServiceImpl extends PayService implements RechargePayService {
    private static final Logger logger = LoggerFactory.getLogger(OrderPayServiceImpl.class);

    @Autowired
    private UserRechargeService userRechargeService;

    @Lazy
    @Autowired
    private WeChatPayService weChatPayService;

    @Autowired
    private UserService userService;

    @Autowired
    private TemplateMessageService templateMessageService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private UserBillService userBillService;

    //订单类
    private UserRecharge userRecharge;




    /**
     * 订单支付
     * @param orderId Integer 订单号
     * @param payType String 支付类型
     *  @author kepler
     * @since 2020-06-22
     * @return PayResponseVo
     */
    @Override
    public CreateOrderResponseVo payOrder(Integer orderId, String payType, String clientIp) {

        try{
            setUserRecharge(userRechargeService.getById(orderId));
            //针对order进行验证, 是否未支付验证
            beforePay();

            //支付需要的参数
            PayParamsVo payParamsVo = new PayParamsVo(
                    getUserRecharge().getOrderId(),
                    getUserRecharge().getRechargeType(),
                    clientIp,
                    getProductName(),
                    getUserRecharge().getPrice(),
                    getUserRecharge().getUid(),
                    new AttachVo(Constants.SERVICE_PAY_TYPE_RECHARGE, getUserRecharge().getUid())
            );

            switch (payType){
                case Constants.PAY_TYPE_WE_CHAT: //微信支付
                    return weChatPayService.create(payParamsVo);//构造下单类
                //TODO 其他支付方式
                default:
                    throw new MallException("未识别当前支付方式！");
            }
        }catch (Exception e){
            throw new MallException(e.getMessage());
        }
    }

    /**
     * 支付之前
     *  @author kepler
     * @since 2020-06-22
     */
    private void beforePay() {
        checkOrderUnPay();
    }


    /**
     * 支付成功
     * @param orderId String 订单号
     * @param userId Integer 用户id
     * @param payType String 支付类型
     *  @author kepler
     * @since 2020-06-22
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, MallException.class})
    @Override
    public boolean success(String orderId, Integer userId, String payType) {
        try{
            UserRecharge userRecharge = new UserRecharge();
            userRecharge.setOrderId(orderId);
            userRecharge.setUid(userId);
            userRecharge = userRechargeService.getInfoByEntity(userRecharge);
            setUserRecharge(userRecharge);

            checkOrderUnPay();
            Boolean complete = userRechargeService.complete(getUserRecharge());//更新订单支付状态
            if(!complete){
                throw new MallException("订单更新失败");
            }

            afterPaySuccess();
            return true;
        }catch (Exception e){
            throw new MallException("订单支付回调失败，" + e.getMessage());
        }
    }

    /**
     * 支付成功处理
     * 增加余额，userBill记录
     * @param userRecharge 充值订单
     */
    @Override
    public Boolean paySuccess(UserRecharge userRecharge) {
        userRecharge.setPaid(true);
        userRecharge.setPayTime(DateUtil.nowDateTime());

        User user = userService.getById(userRecharge.getUid());

        BigDecimal payPrice = userRecharge.getPrice().add(userRecharge.getGivePrice());
        BigDecimal balance = user.getNowMoney().add(payPrice);
        // 余额变动对象
        UserBill userBill = new UserBill();
        userBill.setUid(userRecharge.getUid());
        userBill.setLinkId(userRecharge.getOrderId());
        userBill.setPm(1);
        userBill.setTitle("充值支付");
        userBill.setCategory(Constants.USER_BILL_CATEGORY_MONEY);
        userBill.setType(Constants.USER_BILL_TYPE_PAY_RECHARGE);
        userBill.setNumber(payPrice);
        userBill.setBalance(balance);
        userBill.setMark(StrUtil.format("余额增加了{}元", payPrice));
        userBill.setStatus(1);
        userBill.setCreateTime(DateUtil.nowDateTime());


        UserOperateFundsRequest userOperateFundsRequest = new UserOperateFundsRequest();
        userOperateFundsRequest.setValue(userRecharge.getPrice().add(userRecharge.getGivePrice()));
        userOperateFundsRequest.setFoundsType(Constants.USER_BILL_TYPE_PAY_RECHARGE);
        userOperateFundsRequest.setUid(userRecharge.getUid());
        userOperateFundsRequest.setTitle("充值支付");
        userOperateFundsRequest.setFoundsCategory(Constants.USER_BILL_CATEGORY_MONEY);
        userOperateFundsRequest.setType(1);

        Boolean execute = transactionTemplate.execute(e -> {
            // 订单变动
            userRechargeService.updateById(userRecharge);
            // 余额变动
            userService.operationNowMoney(user.getUid(), payPrice, user.getNowMoney(), "add");
            // 创建记录
            userBillService.save(userBill);
            return Boolean.TRUE;
        });
        if (execute) {
            //下发模板通知
//            pushTempMessageRecharge(userRecharge);
        }
        return execute;
    }

    /**
     * 发送模板消息通知
     */
    private void pushTempMessageRecharge(UserRecharge userRecharge) {
        User info = userService.getById(userRecharge.getUid());

        String tempKey = Constants.WE_CHAT_PUBLIC_TEMP_KEY_RECHARGE;
        if(Constants.PAY_TYPE_WE_CHAT_FROM_PROGRAM.equals(userRecharge.getRechargeType())){
            tempKey = Constants.WE_CHAT_PROGRAM_TEMP_KEY_RECHARGE;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("rechargeOrderId", userRecharge.getOrderId());
        map.put("rechargeAmount", userRecharge.getPrice().add(userRecharge.getGivePrice()).toString());
        map.put("rechargeAfterBalance", info.getNowMoney().toString());
        map.put("rechargeDate", userRecharge.getOrderId());

        templateMessageService.push(tempKey, map, userRecharge.getUid(), userRecharge.getRechargeType());
    }

    /**
     * 支付成功之后, 需要事物处理
     *  @author kepler
     * @since 2020-06-22
     */
    private void afterPaySuccess() {
        try {
            //余额变动
            UserOperateFundsRequest userOperateFundsRequest = new UserOperateFundsRequest();
            userOperateFundsRequest.setValue(getUserRecharge().getPrice().add(getUserRecharge().getGivePrice()));
            userOperateFundsRequest.setFoundsType(Constants.USER_BILL_TYPE_PAY_RECHARGE);
            userOperateFundsRequest.setUid(getUserRecharge().getUid());
            userOperateFundsRequest.setTitle("充值支付");
            userOperateFundsRequest.setFoundsCategory(Constants.USER_BILL_CATEGORY_MONEY);
            userOperateFundsRequest.setType(1);
            userService.updateFounds(userOperateFundsRequest, true);


            //修改订单状态
            getUserRecharge().setPaid(true);
            getUserRecharge().setPayTime(DateUtil.nowDateTime());
            userRechargeService.updateById(getUserRecharge());

            //增加经验、积分
            userService.consumeAfterUpdateUserFounds(getUserRecharge().getUid(), getUserRecharge().getPrice(), Constants.USER_BILL_TYPE_PAY_RECHARGE);

            //下发模板通知
            pushTempMessage();

        }catch (Exception e){
            throw new MallException(e.getMessage());
        }
    }

    /**
     * 发送模板消息通知
     *  @author kepler
     * @since 2020-07-01
     */
    private void pushTempMessage() {
        User info = userService.getById(getUserRecharge().getUid());

        String tempKey = Constants.WE_CHAT_PUBLIC_TEMP_KEY_RECHARGE;
        if(Constants.PAY_TYPE_WE_CHAT_FROM_PROGRAM.equals(getUserRecharge().getRechargeType())){
            tempKey = Constants.WE_CHAT_PROGRAM_TEMP_KEY_RECHARGE;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("rechargeOrderId", getUserRecharge().getOrderId());
        map.put("rechargeAmount", getUserRecharge().getPrice().add(getUserRecharge().getGivePrice()).toString());
        map.put("rechargeAfterBalance", info.getNowMoney().toString());
        map.put("rechargeDate", getUserRecharge().getOrderId());

        templateMessageService.push(tempKey, map, getUserRecharge().getUid(), getUserRecharge().getRechargeType());
    }

    /**
     * 检测是否未支付
     *  @author kepler
     * @since 2020-06-22
     */
    private void checkOrderUnPay() {
        if(null == getUserRecharge()){
            throw new MallException("没有找到订单信息");
        }

        if(getUserRecharge().getPaid()){
            throw new MallException("当前操作被禁止，订单已经被处理");
        }
    }

    /**
     * 获取订单产品名称
     *  @author kepler
     * @since 2020-06-22
     * @return String
     */
    private String getProductName(){

        return "余额充值" + getUserRecharge().getPrice() + "元!";
    }
}
