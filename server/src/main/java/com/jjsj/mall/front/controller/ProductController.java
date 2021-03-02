package com.jjsj.mall.front.controller;


import com.jjsj.common.CommonPage;
import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.category.vo.CategoryTreeVo;
import com.jjsj.mall.store.response.StoreProductReplyResponse;
import com.jjsj.mall.front.request.IndexStoreProductSearchRequest;
import com.jjsj.mall.front.request.ProductRequest;
import com.jjsj.mall.front.response.ProductDetailResponse;
import com.jjsj.mall.front.response.ProductResponse;
import com.jjsj.mall.front.response.StoreProductReplayCountResponse;
import com.jjsj.mall.front.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户 -- 用户中心
 
 */
@Slf4j
@RestController("ProductController")
@RequestMapping("api/front")
@Api(tags = "商品")
public class ProductController {
    @Autowired
    private ProductService productService;


    /**
     * 为你推荐
     *  @author kepler
     * @since 2020-06-02
     */
    @ApiOperation(value = "为你推荐")
    @RequestMapping(value = "/product/hot", method = RequestMethod.GET)
    public CommonResult<CommonPage<ProductResponse>> getMenuUser(@Validated PageParamRequest pageParamRequest){
        IndexStoreProductSearchRequest request = new IndexStoreProductSearchRequest();
        request.setIsHot(true);
        return CommonResult.success(productService.getIndexProduct(request, pageParamRequest));
    }

    /**
     * 获取分类
     *  @author kepler
     * @since 2020-06-03
     */
    @ApiOperation(value = "获取分类")
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public CommonResult<List<CategoryTreeVo>> getCategory(){
        return CommonResult.success(productService.getCategory());
    }

    /**
     * 商品列表
     *  @author kepler
     * @since 2020-06-03
     */
    @ApiOperation(value = "商品列表")
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public CommonResult<CommonPage<ProductResponse>> getList(@Validated ProductRequest request, @Validated PageParamRequest pageParamRequest){
        return CommonResult.success(productService.getList(request, pageParamRequest));
    }

    /**
     * 商品详情
     *  @author kepler
     * @since 2020-06-03
     */
    @ApiOperation(value = "商品详情")
    @RequestMapping(value = "/product/detail/{id}", method = RequestMethod.GET)
    public CommonResult<ProductDetailResponse> getDetail(@PathVariable Integer id){
        return CommonResult.success(productService.getDetail(id));
    }

    /**
     * 商品评论列表
     *  @author kepler
     * @since 2020-06-03
     */
    @ApiOperation(value = "商品评论列表")
    @RequestMapping(value = "/reply/list/{id}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "type", value = "评价等级|0=全部,1=好评,2=中评,3=差评", allowableValues = "range[0,1,2,3]")
    public CommonResult<CommonPage<StoreProductReplyResponse>> getReplyList(
            @PathVariable Integer id,
            @RequestParam(value = "type") Integer type,
            @Validated PageParamRequest pageParamRequest){
        return CommonResult.success(CommonPage.restPage(productService.getReplyList(id, type, pageParamRequest)));
    }

    /**
     * 商品评论数量
     *  @author kepler
     * @since 2020-06-03
     */
    @ApiOperation(value = "商品评论数量")
    @RequestMapping(value = "/reply/config/{id}", method = RequestMethod.GET)
    public CommonResult<StoreProductReplayCountResponse> getReplyCount(@PathVariable Integer id){
        return CommonResult.success(productService.getReplyCount(id));
    }
}



