package com.jjsj.mall.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjsj.common.PageParamRequest;
import com.jjsj.constants.Constants;
import com.jjsj.exception.MallException;
import com.github.pagehelper.PageHelper;
import com.jjsj.mall.category.service.CategoryService;
import com.jjsj.mall.category.vo.CategoryTreeVo;
import com.jjsj.utils.MallUtil;
import com.jjsj.mall.system.dao.SystemRoleDao;
import com.jjsj.mall.system.model.SystemAdmin;
import com.jjsj.mall.system.model.SystemRole;
import com.jjsj.mall.system.request.SystemRoleSearchRequest;
import com.jjsj.mall.system.service.SystemAdminService;
import com.jjsj.mall.system.service.SystemRoleService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SystemRoleServiceImpl 接口实现

 */
@Service
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleDao, SystemRole> implements SystemRoleService {

    @Resource
    private SystemRoleDao dao;

    @Autowired
    private SystemAdminService systemAdminService;

    @Autowired
    private CategoryService categoryService;


    /**
    * 列表
    * @param request 请求参数
    * @param pageParamRequest 分页类参数
    *  @author kepler
    * @since 2020-04-18
    * @return List<SystemRole>
    */
    @Override
    public List<SystemRole> getList(SystemRoleSearchRequest request, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<SystemRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq("level", request.getLevel());
        if(null != request.getStatus())
        lambdaQueryWrapper.eq(SystemRole::getStatus, request.getStatus());
//        queryWrapper.eq("rules", request.getRules());
        if(null != request.getRoleName())
        lambdaQueryWrapper.like(SystemRole::getRoleName, request.getRoleName());
        lambdaQueryWrapper.orderByAsc(SystemRole::getId);
        return dao.selectList(lambdaQueryWrapper);
    }

    /**
     * 根据id集合获取对应权限列表
     * @param ids id集合
     * @return 对应的权限列表
     */
    @Override
    public List<SystemRole> getListInIds(List<Integer> ids) {
//        LambdaQueryWrapper<SystemRole> lqw = new LambdaQueryWrapper<>();
//        lqw.in(SystemRole::getId, ids);
        return dao.selectBatchIds(ids);
    }

    /**
     * 检测是否有访问菜单接口取的权限
     * @param uri String 请求参数
     *  @author kepler
     * @since 2020-07-06
     * @return Boolean
     */
    @Override
    public Boolean checkAuth(String uri) {
        //查询当前路由是否配置了权限，如果没有配置则直接通过
        if(!categoryService.checkUrl(uri)){
            return true;
        }

        List<Integer> categoryIdList = getRoleListInRoleId();
        if(categoryIdList.size() < 1){
            return false;
        }

        //查询分类，根据in id和 路由
        return categoryService.checkAuth(categoryIdList, uri);
    }

    /**
     * 带结构的无线级分类
     *  @author kepler
     * @since 2020-04-16
     */
    @Override
    public List<CategoryTreeVo> menu() {
        List<Integer> categoryIdList = getRoleListInRoleId();
        System.out.println("权限列表:categoryIdList:"+ JSON.toJSONString(categoryIdList));
        return categoryService.getListTree(Constants.CATEGORY_TYPE_MENU, 1, categoryIdList);
    }

    @Override
    public Boolean updateStatus(Integer id, Boolean status) {
        SystemRole role = getById(id);
        if (ObjectUtil.isNull(role)) {
            throw new MallException("身份不存在");
        }
        if (role.getStatus().equals(status)) {
            return true;
        }
        role.setStatus(status);
        return updateById(role);
    }

    private List<Integer> getRoleListInRoleId(){
        //获取当前用户的所有权限
        SystemAdmin systemAdmin = systemAdminService.getInfo();
        if(null == systemAdmin || StringUtils.isBlank(systemAdmin.getRoles())){
            throw new MallException("没有权限访问！");
        }

        //获取用户权限组
        List<SystemRole> systemRoleList = getVoListInId(systemAdmin.getRoles());
        if(systemRoleList.size() < 1){
            throw new MallException("没有权限访问！");
        }

        //获取用户权限规则
        List<Integer> categoryIdList = new ArrayList<>();
        for (SystemRole systemRole : systemRoleList) {
            if(StringUtils.isBlank(systemRole.getRules())){
                continue;
            }

            categoryIdList.addAll(MallUtil.stringToArray(systemRole.getRules()));
        }

        return categoryIdList;
    }

    private List<SystemRole> getVoListInId(String roles) {
        LambdaQueryWrapper<SystemRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SystemRole::getId, MallUtil.stringToArray(roles));
        return dao.selectList(lambdaQueryWrapper);
    }

}

