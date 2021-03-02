package com.jjsj.mall.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageInfo;
import com.jjsj.mall.system.response.SystemStoreStaffResponse;
import com.jjsj.mall.system.model.SystemStoreStaff;
import com.jjsj.mall.system.request.SystemStoreStaffRequest;

import java.util.HashMap;
import java.util.List;

/**
 * SystemStoreStaffService 接口

 */
public interface SystemStoreStaffService extends IService<SystemStoreStaff> {

    PageInfo<SystemStoreStaffResponse> getList(Integer storeId, PageParamRequest pageParamRequest);

    HashMap<Integer, SystemStoreStaff> getMapInId(List<Integer> clerkIdList);

    /**
     * 根据用户id获取核销信息
     * @param userIds    用户id集合
     * @return          核销信息
     */
    List<SystemStoreStaff> getByAdminUserIds(List<Integer> userIds);

    /**
     *      添加核销员 唯一验证
     * @param request  当前添加参数
     * @return                  添加结果
     */
    boolean saveUnique(SystemStoreStaffRequest request);
}