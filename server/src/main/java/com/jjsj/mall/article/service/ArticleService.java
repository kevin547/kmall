package com.jjsj.mall.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageInfo;
import com.jjsj.mall.article.model.Article;
import com.jjsj.mall.article.request.ArticleSearchRequest;
import com.jjsj.mall.article.vo.ArticleVo;

/**
*  ArticleService 接口

*/
public interface ArticleService extends IService<Article> {

    PageInfo<ArticleVo> getList(ArticleSearchRequest request, PageParamRequest pageParamRequest);

    boolean update(Integer id, Integer productId);

    ArticleVo getVoByFront(Integer id);
}
