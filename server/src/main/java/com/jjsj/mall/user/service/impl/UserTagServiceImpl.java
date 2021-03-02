package com.jjsj.mall.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjsj.common.PageParamRequest;
import com.jjsj.utils.MallUtil;
import com.jjsj.mall.user.dao.UserTagDao;
import com.jjsj.mall.user.model.UserTag;
import com.jjsj.mall.user.service.UserTagService;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * UserTagServiceImpl 接口实现
 */
@Service
public class UserTagServiceImpl extends ServiceImpl<UserTagDao, UserTag> implements UserTagService {

    @Resource
    private UserTagDao dao;


    /**
     * 列表
     *
     * @param pageParamRequest 分页类参数
     * @return List<UserTag>
     *  @author kepler
     * @since 2020-06-05
     */
    @Override
    public List<UserTag> getList(PageParamRequest pageParamRequest) {
        return dao.selectList(null);
    }


    /**
     * 检测是否有标签已经废弃
     *
     * @param tagIdValue String 标签id
     * @return List<UserTag>
     *  @author kepler
     * @since 2020-06-05
     */
    @Override
    public String clean(String tagIdValue) {
        LambdaQueryWrapper<UserTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(UserTag::getId, MallUtil.stringToArray(tagIdValue));
        List<UserTag> userTags = dao.selectList(lambdaQueryWrapper);
        if (null == userTags) {
            return null;
        }

        return userTags.stream().map(s -> s.getId().toString()).distinct()
            .collect(Collectors.joining(","));
    }

    /**
     * 根据id in 返回name字符串
     *
     * @param tagIdValue String 标签id
     * @return List<UserTag>
     *  @author kepler
     * @since 2020-06-05
     */
    @Override
    public String getGroupNameInId(String tagIdValue) {
        LambdaQueryWrapper<UserTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(UserTag::getId, MallUtil.stringToArray(tagIdValue))
            .orderByDesc(UserTag::getId);
        List<UserTag> userTags = dao.selectList(lambdaQueryWrapper);
        if (null == userTags) {
            return "无";
        }

        return userTags.stream().map(UserTag::getName).distinct().collect(Collectors.joining(","));
    }

}

