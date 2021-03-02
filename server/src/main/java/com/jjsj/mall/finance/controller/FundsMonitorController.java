package com.jjsj.mall.finance.controller;

import com.jjsj.common.CommonPage;
import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.finance.model.UserFundsMonitor;
import com.jjsj.mall.finance.request.FundsMonitorRequest;
import com.jjsj.mall.finance.request.FundsMonitorUserSearchRequest;
import com.jjsj.mall.finance.service.UserFundsMonitorService;
import com.jjsj.mall.user.model.UserBrokerageRecord;
import com.jjsj.mall.user.response.BillType;
import com.jjsj.mall.user.response.UserBillResponse;
import com.jjsj.mall.user.service.UserBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 用户提现表 前端控制器
 
 */
@Slf4j
@RestController
@RequestMapping("api/admin/finance/founds/monitor")
@Api(tags = "财务 -- 资金监控")

public class FundsMonitorController {

    @Autowired
    private UserBillService userBillService;

    @Autowired
    private UserFundsMonitorService userFundsMonitorService;

    /**
     * 分页显示资金监控
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     *  @author kepler
     * @since 2020-05-11
     */
    @ApiOperation(value = "资金监控")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserBillResponse>>  getList(@Validated FundsMonitorRequest request, @Validated PageParamRequest pageParamRequest){
        CommonPage<UserBillResponse> userExtractCommonPage = CommonPage.restPage(userBillService.fundMonitoring(request, pageParamRequest));
        return CommonResult.success(userExtractCommonPage);
    }

    /**
     * 资金监控查询参数
     * @return 资金监控查询参数集合
     */
    @ApiOperation(value = "资金监控--明细类型查询数据")
    @RequestMapping(value = "/list/option", method = RequestMethod.GET)
    public CommonResult<Object>  getList(){
        return CommonResult.success(userBillService.getSearchOption());
    }

    /**
     * 佣金记录
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     *  @author kepler
     * @since 2020-05-11
     */
    @ApiOperation(value = "佣金记录")
    @RequestMapping(value = "/list/user", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserFundsMonitor>>  user(@Validated FundsMonitorUserSearchRequest request, @Validated PageParamRequest pageParamRequest){
        CommonPage<UserFundsMonitor> userFundsMonitorCommonPage =
                CommonPage.restPage(userFundsMonitorService.getFundsMonitor(request, pageParamRequest));
        return CommonResult.success(userFundsMonitorCommonPage);
    }

    /**
     * 佣金详细记录
     * @param pageParamRequest  分页参数
     * @param userId    被查询的用户id
     * @return  佣金查询结果
     */
    @ApiOperation(value = "佣金详细记录")
    @RequestMapping(value = "/list/user/detail/{userId}", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserBrokerageRecord>> userDetail(PageParamRequest pageParamRequest,
                                                                    @RequestParam(value = "dateLimit", required = false) String dateLimit,
                                                                    @PathVariable Integer userId){
        CommonPage<UserBrokerageRecord> commonPage = CommonPage.restPage(userFundsMonitorService.getFundsMonitorDetail(userId, dateLimit, pageParamRequest));
        return CommonResult.success(commonPage);
    }


    /**
     * 获取资金操作类型列表
     * @return 查询结果
     */
    @ApiOperation(value="资金操作类型")
    @RequestMapping(value = "/list/billtype", method = RequestMethod.GET)
    public CommonResult<List<BillType>> getBillTypeList(){
        return CommonResult.success(userBillService.getBillType());
    }
}



