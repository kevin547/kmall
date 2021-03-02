package com.jjsj.mall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.user.model.UserGroup;
import java.util.List;

/**
 * UserGroupService 接口实现
 */
public interface UserGroupService extends IService<UserGroup> {

    List<UserGroup> getList(PageParamRequest pageParamRequest);

    String clean(String groupIdValue);

    String getGroupNameInId(String groupIdValue);
}