package com.jjsj.mall.category.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.category.model.Category;
import com.jjsj.mall.category.request.CategoryRequest;
import com.jjsj.mall.category.request.CategorySearchRequest;
import com.jjsj.mall.category.vo.CategoryTreeVo;

import java.util.HashMap;
import java.util.List;

/**
*   CategoryService 接口

*/
public interface CategoryService extends IService<Category> {
    List<Category> getList(CategorySearchRequest request, PageParamRequest pageParamRequest);

    int delete(Integer id);

    String getPathByPId(Integer pid);

    List<CategoryTreeVo> getListTree(Integer type, Integer status, String name);
    List<CategoryTreeVo> getListTree(Integer type, Integer status, List<Integer> categoryIdList);

    List<Category> getByIds(List<Integer> ids);

    HashMap<Integer, String> getListInId(List<Integer> cateIdList);

    Boolean checkAuth(List<Integer> pathIdList, String uri);

    boolean update(CategoryRequest request, Integer id);

    List<Category> getChildVoListByPid(Integer pid);

    int checkName(String name, Integer type);

    boolean checkUrl(String uri);

    boolean updateStatus(Integer id);

    /**
     * 新增分类表
     */
    Boolean create(CategoryRequest categoryRequest);
}
