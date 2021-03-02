package com.jjsj.mall.front.controller;

import com.jjsj.common.CommonPage;
import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.store.model.StoreProduct;
import com.jjsj.mall.store.model.StoreProductRelation;
import com.jjsj.mall.store.request.StoreProductRelationSearchRequest;
import com.jjsj.mall.store.service.StoreProductRelationService;
import com.jjsj.mall.front.request.UserCollectAllRequest;
import com.jjsj.mall.front.request.UserCollectRequest;
import com.jjsj.mall.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 商品点赞和收藏表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/front/collect")
@Api(tags = "用户 -- 点赞/收藏")

public class UserCollectController {

    @Autowired
    private StoreProductRelationService storeProductRelationService;

    @Autowired
    private UserService userService;

    /**
     * 获取收藏产品
     * @param pageParamRequest 分页参数
     *  @author kepler
     * @since 2020-05-06
     */
    @ApiOperation(value = "获取收藏产品")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreProduct>> getList(@Validated PageParamRequest pageParamRequest){
        StoreProductRelationSearchRequest storeProductRelation = new StoreProductRelationSearchRequest();
        storeProductRelation.setUid(userService.getUserIdException());

        CommonPage<StoreProduct> storeProductCommonPage =
                CommonPage.restPage(storeProductRelationService.getList(storeProductRelation, pageParamRequest));
        return CommonResult.success(storeProductCommonPage);
    }

    /**
     * 添加收藏产品
     * @param request StoreProductRelationRequest 新增参数
     *  @author kepler
     * @since 2020-05-06
     */
    @ApiOperation(value = "添加收藏产品")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated UserCollectRequest request){
        StoreProductRelation storeProductRelation = new StoreProductRelation();
        BeanUtils.copyProperties(request, storeProductRelation);
        storeProductRelation.setUid(userService.getUserIdException());
        if(storeProductRelationService.save(storeProductRelation)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 添加收藏产品
     * @param request UserCollectAllRequest 新增参数
     *  @author kepler
     * @since 2020-05-06
     */
    @ApiOperation(value = "批量收藏")
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public CommonResult<String> all(@RequestBody @Validated UserCollectAllRequest request){
        if(storeProductRelationService.all(request)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 取消收藏产品
     *  @author kepler
     * @since 2020-05-06
     */
    @ApiOperation(value = "取消收藏产品")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public CommonResult<String> delete(@RequestBody @Validated UserCollectRequest request){
        if(storeProductRelationService.delete(request)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }
}



