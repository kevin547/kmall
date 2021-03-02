package com.jjsj.mall.finance.service;

import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageInfo;
import com.jjsj.mall.finance.response.BalanceResponse;
import com.jjsj.mall.finance.response.UserExtractResponse;
import com.jjsj.mall.finance.model.UserExtract;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.finance.request.UserExtractRequest;
import com.jjsj.mall.finance.request.UserExtractSearchRequest;
import com.jjsj.mall.front.response.UserExtractRecordResponse;

import java.math.BigDecimal;
import java.util.List;

/**
* UserExtractService 接口

*/
public interface UserExtractService extends IService<UserExtract> {

    List<UserExtract> getList(UserExtractSearchRequest request, PageParamRequest pageParamRequest);

    /**
     * 提现总金额
     */
    BalanceResponse getBalance(String dateLimit);

    /**
     * 提现总金额
     *  @author kepler
     * @since 2020-05-11
     * @return BalanceResponse
     */
    BigDecimal getWithdrawn(String startTime,String endTime);

    /**
     * 审核中总金额
     *  @author kepler
     * @since 2020-05-11
     * @return BalanceResponse
     */
    BigDecimal getWithdrawning(String startTime, String endTime);

    /**
     * 获取待提现总金额
     * @return 待提现总金额
     */
    BigDecimal getWaiteForDrawn(String startTime,String endTime);

    Boolean create(UserExtractRequest request, Integer userId);

//    BigDecimal getToBeWihDraw(Integer userId);

    BigDecimal getFreeze(Integer userId);

    UserExtractResponse getUserExtractByUserId(Integer userId);

    List<UserExtract> getListByUserIds(List<Integer> userIds);

    /**
     * 提现审核
     * @param id    提现申请id
     * @param status 审核状态 -1 未通过 0 审核中 1 已提现
     * @param backMessage   驳回原因
     * @return  审核结果
     */
    Boolean updateStatus(Integer id,Integer status,String backMessage);

    PageInfo<UserExtractRecordResponse> getExtractRecord(Integer userId, PageParamRequest pageParamRequest);

    BigDecimal getExtractTotalMoney(Integer userId);

    /**
     * 提现申请
     * @return
     */
    Boolean extractApply(UserExtractRequest request);
}
