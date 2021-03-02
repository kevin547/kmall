package com.jjsj.mall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.user.model.UserTag;
import java.util.List;

/**
 * UserTagService 接口实现
 */
public interface UserTagService extends IService<UserTag> {

    List<UserTag> getList(PageParamRequest pageParamRequest);

    String clean(String tagIdValue);

    String getGroupNameInId(String tagIdValue);
}