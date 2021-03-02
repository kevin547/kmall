package com.jjsj.mall.front.controller;

import com.jjsj.common.CommonPage;
import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.constants.Constants;
import com.jjsj.mall.article.request.ArticleSearchRequest;
import com.jjsj.mall.article.service.ArticleService;
import com.jjsj.mall.article.vo.ArticleVo;
import com.jjsj.mall.category.service.CategoryService;
import com.jjsj.mall.category.vo.CategoryTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 文章
 
 */
@Slf4j
@RestController("ArticleFrontController")
@RequestMapping("api/front/article")
@Api(tags = "文章")

public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 分页列表
     * @param cid String 搜索条件
     * @param pageParamRequest 分页参数
     *  @author kepler
     * @since 2020-04-18
     */
    @ApiOperation(value = "分页列表")
    @RequestMapping(value = "/list/{cid}", method = RequestMethod.GET)
    public CommonResult<CommonPage<ArticleVo>>  getList(@PathVariable(name="cid") String cid,
                                                      @Validated PageParamRequest pageParamRequest){
        ArticleSearchRequest request = new ArticleSearchRequest();
        request.setCid(cid);
        request.setHide(false);
        request.setStatus(false);
        CommonPage<ArticleVo> articleCommonPage = CommonPage.restPage(articleService.getList(request, pageParamRequest));
        return CommonResult.success(articleCommonPage);
    }

    /**
     * 热门列表
     *  @author kepler
     * @since 2020-04-18
     */
    @ApiOperation(value = "热门列表")
    @RequestMapping(value = "/hot/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<ArticleVo>>  getHotList(){
        ArticleSearchRequest request = new ArticleSearchRequest();
        request.setIsHot(true);
        request.setHide(false);
        request.setStatus(false);
        CommonPage<ArticleVo> articleCommonPage = CommonPage.restPage(articleService.getList(request, new PageParamRequest()));
        return CommonResult.success(articleCommonPage);
    }

    /**
     * 轮播列表
     *  @author kepler
     * @since 2020-04-18
     */
    @ApiOperation(value = "轮播列表")
    @RequestMapping(value = "/banner/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<ArticleVo>>  getList(){
        ArticleSearchRequest request = new ArticleSearchRequest();
        request.setIsBanner(true);
        request.setHide(false);
        request.setStatus(false);
        CommonPage<ArticleVo> articleCommonPage = CommonPage.restPage(articleService.getList(request, new PageParamRequest()));
        return CommonResult.success(articleCommonPage);
    }

    /**
     * 分类列表
     *  @author kepler
     * @since 2020-04-18
     */
    @ApiOperation(value = "分类列表")
    @RequestMapping(value = "/category/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<CategoryTreeVo>> categoryList(){
        return CommonResult.success(CommonPage.restPage(categoryService.getListTree(Constants.CATEGORY_TYPE_ARTICLE, 1,"")));
    }

    /**
     * 查询文章详情
     * @param id Integer
     *  @author kepler
     * @since 2020-04-18
     */
    @ApiOperation(value = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiImplicitParam(name="id", value="文章ID")
    public CommonResult<ArticleVo> info(@RequestParam(value = "id") Integer id){
        return CommonResult.success(articleService.getVoByFront(id));
   }
}



