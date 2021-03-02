package com.jjsj.mall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageInfo;
import com.jjsj.mall.finance.request.FundsMonitorRequest;
import com.jjsj.mall.finance.request.FundsMonitorSearchRequest;
import com.jjsj.mall.front.response.UserSpreadCommissionResponse;
import com.jjsj.mall.store.request.StoreOrderRefundRequest;
import com.jjsj.mall.user.response.BillType;
import com.jjsj.mall.user.response.UserBillResponse;
import com.jjsj.mall.user.model.User;
import com.jjsj.mall.user.model.UserBill;
import com.jjsj.mall.user.request.UserBillDetailListRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * UserBillService 接口实现
 */
public interface UserBillService extends IService<UserBill> {

    /**
     * 列表
     *
     * @param request 请求参数
     * @param pageParamRequest 分页类参数
     * @return List<UserBill>
     *  @author kepler
     * @since 2020-04-28
     */
    List<UserBill> getList(FundsMonitorSearchRequest request, PageParamRequest pageParamRequest);

    /**
     * 新增/消耗 总数
     *
     * @param pm Integer 0 = 支出 1 = 获得
     * @param userId Integer 用户uid
     * @param category String 类型
     * @param date String 时间范围
     * @param type String 小类型
     * @return UserBill
     *  @author kepler
     * @since 2020-05-29
     */
    Integer getSumInteger(Integer pm, Integer userId, String category, String date, String type);

    /**
     * 新增/消耗  总金额
     *
     * @param pm Integer 0 = 支出 1 = 获得
     * @param userId Integer 用户uid
     * @param category String 类型
     * @param date String 时间范围
     * @param type String 小类型
     * @return UserBill
     *  @author kepler
     * @since 2020-05-29
     */
    BigDecimal getSumBigDecimal(Integer pm, Integer userId, String category, String date,
        String type);

    /**
     * 按照月份分组, 余额
     *
     * @return CommonPage<UserBill>
     *  @author kepler
     * @since 2020-06-08
     */
    PageInfo<UserSpreadCommissionResponse> getListGroupByMonth(Integer userId,
        List<String> typeList, PageParamRequest pageParamRequest, String category);

    /**
     * 保存退款日志
     *
     * @return boolean
     *  @author kepler
     * @since 2020-06-08
     */
    boolean saveRefundBill(StoreOrderRefundRequest request, User user);

    /**
     * 反还佣金日志
     *
     *  @author kepler
     * @since 2020-06-08
     */
    void saveRefundBrokeragePriceBill(StoreOrderRefundRequest request, User user);

    /**
     * 反还积分日志
     *
     *  @author kepler
     * @since 2020-06-08
     */
    void saveRefundIntegralBill(StoreOrderRefundRequest request, User user);

    /**
     * 根据用户id获取对应的佣金数据 分销using
     *
     * @param userId 用户id
     * @return 佣金数据
     */
    BigDecimal getDataByUserId(Integer userId);

    /**
     * 通过订单获取
     *
     * @param id 订单id
     * @param userId 用户id
     * @param pm 类型
     */
    BigDecimal getIntegerByOrder(Integer id, Integer userId, int pm);

    /**
     * 列表
     *
     * @param request 请求参数
     * @param pageParamRequest 分页类参数
     * @return List<UserBill>
     *  @author kepler
     * @since 2020-04-28
     */
    PageInfo<UserBillResponse> getListAdmin(FundsMonitorSearchRequest request,
        PageParamRequest pageParamRequest);

    /**
     * 列表
     *
     * @param request 请求参数
     * @param monthList List<String> 分页类参数
     * @return List<UserBill>
     *  @author kepler
     * @since 2020-04-28
     */
    Map<String, Integer> getCountListByMonth(FundsMonitorSearchRequest request,
        List<String> monthList);

    /**
     * 佣金排行榜
     *
     * @param type String 时间范围
     * @param pageParamRequest PageParamRequest 分页
     * @return List<LoginResponse>
     *  @author kepler
     * @since 2020-05-25
     */
    List<UserBill> getTopBrokerageListByDate(String type, PageParamRequest pageParamRequest);

    /**
     * 获取资金操作类型
     *
     * @return 操作类型集合，从数据库group by(type)查询获取
     */
    List<UserBill> getBillGroupType();

    /**
     * 返回资金操作类型 仅仅转换数据用
     *
     * @return 操作类型
     */
    List<BillType> getBillType();

    /**
     * 获取佣金总额
     *
     * @return 佣金总额
     */
    BigDecimal getSumBrokerage();

    /**
     * 根据基本条件查询
     *
     * @param bill 基本参数
     * @return 查询结果
     */
    List<UserBill> getByEntity(UserBill bill);

    /**
     * Base serch
     *
     * @param userId 用户Id
     * @param request 查询参数
     * @param pageParamRequest 分页参数
     * @return 查询结果
     */
    PageInfo<UserBillResponse> getByBaseSearch(Integer userId, UserBillDetailListRequest request,
        PageParamRequest pageParamRequest);

    /**
     * 查询搜索明细类型参数
     *
     * @return 明细类型集合
     */
    List<UserBill> getSearchOption();

    /**
     * 获取订单历史处理记录(退款使用)
     *
     * @param orderId 订单id
     * @param uid 用户id
     */
    List<UserBill> findListByOrderIdAndUid(Integer orderId, Integer uid);

    /**
     * 资金监控
     *
     * @param request 查询参数
     * @param pageParamRequest 分页参数
     * @return PageInfo
     */
    PageInfo<UserBillResponse> fundMonitoring(FundsMonitorRequest request,
        PageParamRequest pageParamRequest);

    /**
     * 用户账单记录（现金）
     *
     * @param uid 用户uid
     * @param type 记录类型：all-全部，expenditure-支出，income-收入
     */
    PageInfo<UserBill> nowMoneyBillRecord(Integer uid, String type, PageParamRequest pageRequest);
}