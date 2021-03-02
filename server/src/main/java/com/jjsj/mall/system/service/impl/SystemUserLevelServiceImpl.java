package com.jjsj.mall.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageHelper;
import com.jjsj.mall.system.dao.SystemUserLevelDao;
import com.jjsj.mall.system.model.SystemUserLevel;
import com.jjsj.mall.system.request.SystemUserLevelRequest;
import com.jjsj.mall.system.request.SystemUserLevelSearchRequest;
import com.jjsj.mall.system.service.SystemAttachmentService;
import com.jjsj.mall.system.service.SystemUserLevelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * SystemUserLevelServiceImpl 接口实现

 */
@Service
public class SystemUserLevelServiceImpl extends ServiceImpl<SystemUserLevelDao, SystemUserLevel> implements SystemUserLevelService {

    @Resource
    private SystemUserLevelDao dao;

    @Autowired
    private SystemAttachmentService systemAttachmentService;


    /**
     * 分页显示设置用户等级表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     *  @author kepler
     * @since 2020-04-13
     * @return List<SystemUserLevel>
     */
    @Override
    public List<SystemUserLevel> getList(SystemUserLevelSearchRequest request, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<SystemUserLevel> levelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isBlank(request.getName())){
            levelLambdaQueryWrapper.like(SystemUserLevel::getName, request.getName());
        }

        if(request.getIsShow() != null){
            levelLambdaQueryWrapper.eq(SystemUserLevel::getIsShow, request.getIsShow());
        }

        levelLambdaQueryWrapper.eq(SystemUserLevel::getIsDel, false);
        levelLambdaQueryWrapper.orderByAsc(SystemUserLevel::getGrade);
        return dao.selectList(levelLambdaQueryWrapper);
    }

    /**
     * 根据等级id获取比其等级小的所有信息
     * @param levelId integer 等级
     *  @author kepler
     * @since 2020-04-10
     * @return List<SystemUserLevel>
     */
    @Override
    public List<SystemUserLevel> getGradeListByLevelId(Integer levelId) {
        SystemUserLevel systemUserLevel = dao.selectById(levelId);
        LambdaQueryWrapper<SystemUserLevel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SystemUserLevel::getIsShow, 1);
        lambdaQueryWrapper.eq(SystemUserLevel::getIsDel, 0);
        lambdaQueryWrapper.le(SystemUserLevel::getGrade, systemUserLevel.getGrade());
        return dao.selectList(lambdaQueryWrapper);
    }

    /**
     * 新增设置用户等级表
     * @param request SystemUserLevelRequest 新增参数
     *  @author kepler
     * @since 2020-04-13
     * @return boolean
     */
    @Override
    public boolean create(SystemUserLevelRequest request) {
        SystemUserLevel systemUserLevel = new SystemUserLevel();
        BeanUtils.copyProperties(request, systemUserLevel);
        clearPrefix(systemUserLevel);
        return save(systemUserLevel);
    }

    /**
     * 修改设置用户等级表
     * @param id integer id
     * @param request 修改参数
     *  @author kepler
     * @since 2020-04-13
     * @return boolean
     */
    @Override
    public boolean update(Integer id, SystemUserLevelRequest request) {
        SystemUserLevel systemUserLevel = new SystemUserLevel();
        BeanUtils.copyProperties(request, systemUserLevel);
        systemUserLevel.setId(id);
        clearPrefix(systemUserLevel);
        return updateById(systemUserLevel);
    }

    /**
     * 清除前缀
     * @param systemUserLevel 需要去掉前缀的参数所在的对象
     *  @author kepler
     * @since 2020-04-13
     */
    private void clearPrefix(SystemUserLevel systemUserLevel) {
        systemUserLevel.setImage(systemAttachmentService.clearPrefix(systemUserLevel.getImage()));
        systemUserLevel.setIcon(systemAttachmentService.clearPrefix(systemUserLevel.getIcon()));
    }

    @Override
    public SystemUserLevel getByLevelId(Integer levelId) {
        LambdaQueryWrapper<SystemUserLevel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SystemUserLevel::getIsShow, 1);
        lambdaQueryWrapper.eq(SystemUserLevel::getIsDel, 0);
        lambdaQueryWrapper.eq(SystemUserLevel::getId, levelId);
        return dao.selectOne(lambdaQueryWrapper);
    }
}

