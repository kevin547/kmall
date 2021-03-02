package com.jjsj.mall.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageHelper;
import com.jjsj.utils.MallUtil;
import com.jjsj.mall.user.dao.UserGroupDao;
import com.jjsj.mall.user.model.UserGroup;
import com.jjsj.mall.user.service.UserGroupService;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * UserGroupServiceImpl 接口实现
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupDao, UserGroup> implements
    UserGroupService {

    @Resource
    private UserGroupDao dao;


    /**
     * 列表
     *
     * @param pageParamRequest 分页类参数
     * @return List<UserGroup>
     *  @author kepler
     * @since 2020-04-28
     */
    @Override
    public List<UserGroup> getList(PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        return dao.selectList(null);
    }

    /**
     * 检测是否有分组已经废弃
     *
     * @param groupIdValue String 分组id
     * @return List<UserTag>
     *  @author kepler
     * @since 2020-06-05
     */
    @Override
    public String clean(String groupIdValue) {
        LambdaQueryWrapper<UserGroup> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(UserGroup::getId, MallUtil.stringToArray(groupIdValue));
        List<UserGroup> userTags = dao.selectList(lambdaQueryWrapper);
        if (null == userTags) {
            return null;
        }

        return userTags.stream().map(s -> s.getId().toString()).distinct()
            .collect(Collectors.joining(","));
    }

    /**
     * 根据id in，返回字符串拼接
     *
     * @param groupIdValue String 分组id
     * @return List<UserTag>
     *  @author kepler
     * @since 2020-06-05
     */
    @Override
    public String getGroupNameInId(String groupIdValue) {
        LambdaQueryWrapper<UserGroup> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(UserGroup::getId, MallUtil.stringToArray(groupIdValue))
            .orderByDesc(UserGroup::getId);
        List<UserGroup> userTags = dao.selectList(lambdaQueryWrapper);
        if (null == userTags) {
            return "无";
        }

        return userTags.stream().map(UserGroup::getGroupName).distinct()
            .collect(Collectors.joining(","));
    }

}

