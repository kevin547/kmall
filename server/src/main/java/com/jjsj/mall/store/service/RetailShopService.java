package com.jjsj.mall.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.CommonPage;
import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageInfo;
import com.jjsj.mall.store.response.RetailShopStatisticsResponse;
import com.jjsj.mall.store.request.RetailShopRequest;
import com.jjsj.mall.store.request.RetailShopStairUserRequest;
import com.jjsj.mall.user.model.User;
import com.jjsj.mall.user.response.SpreadUserResponse;
import com.jjsj.mall.user.response.UserResponse;

import java.util.List;

/**
 * 分销业务

 */
public interface RetailShopService extends IService<User> {

    /**
     * 分销员列表
     * @param keywords 搜索参数
     * @param dateLimit 时间参数
     * @param pageRequest 分页参数
     * @return
     */
    CommonPage<SpreadUserResponse> getSpreadPeopleList(String keywords, String dateLimit, PageParamRequest pageRequest);

    /**
     * 获取分销头部数据
     * @param nickName 查询参数
     * @param dateLimit 时间参数对象
     */
    List<UserResponse> getStatisticsData(String nickName, String dateLimit);

    /**
     * 统计推广人员列表
     * @param request 查询参数
     * @return 推广人员集合列表
     */
    PageInfo<User> getStairUsers(RetailShopStairUserRequest request, PageParamRequest pageParamRequest);

    /**
     * 获取分销配置
     * @return 分销配置信息
     */
    RetailShopRequest getManageInfo();

    /**
     * 保存或者更新分销配置信息
     * @param retailShopRequest 待保存数据
     * @return 保存结果
     */
    boolean setManageInfo(RetailShopRequest retailShopRequest);

    /**
     * 获取分销统计数据
     * @param keywords  模糊搜索参数
     * @param dateLimit 时间参数
     * @return
     */
    RetailShopStatisticsResponse getAdminStatistics(String keywords, String dateLimit);
}
