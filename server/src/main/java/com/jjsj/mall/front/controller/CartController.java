package com.jjsj.mall.front.controller;

import com.jjsj.common.CommonPage;
import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.jjsj.exception.MallException;
import com.jjsj.mall.store.model.StoreCart;
import com.jjsj.mall.store.response.StoreCartResponse;
import com.jjsj.mall.store.service.StoreCartService;
import com.jjsj.mall.front.request.CartRequest;
import com.jjsj.mall.front.request.CartResetRequest;
import com.jjsj.mall.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 购物车 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/front/cart")
@Api(tags = "商品 -- 购物车") //配合swagger使用
public class CartController {

    @Autowired
    private StoreCartService storeCartService;

    @Autowired
    private UserService userService;

    /**
     * 分页显示购物车表
     * @param pageParamRequest 分页参数
     *  @author kepler
     * @since 2020-05-28
     */
    @ApiOperation(value = "分页列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreCartResponse>> getList(@RequestParam Boolean isValid, @Validated PageParamRequest pageParamRequest){
        StoreCart storeCart = new StoreCart();
        CommonPage<StoreCartResponse> storeCartCommonPage =
                CommonPage.restPage(storeCartService.getList(pageParamRequest, isValid));
        return CommonResult.success(storeCartCommonPage);
    }

    /**
     * 新增购物车表
     * @param storeCartRequest 新增参数
     *  @author kepler
     * @since 2020-05-28
     */
    @ApiOperation(value = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<HashMap<String,String>> save(@RequestBody @Validated CartRequest storeCartRequest){
        StoreCart storeCart = new StoreCart();
        BeanUtils.copyProperties(storeCartRequest, storeCart);
        String cartId = storeCartService.saveCate(storeCart);
        if(StringUtils.isNotBlank(cartId)){
            HashMap<String,String> result = new HashMap<>();
            result.put("cartId", cartId);
            return CommonResult.success(result);
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 删除购物车表
     * @param ids List<Integer> edit by stivepeim 2020-7-4
     *  @author kepler
     * @since 2020-05-28
     */
    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResult<String> delete(@RequestParam(value = "ids") List<Integer> ids){
        if(storeCartService.removeByIds(ids)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }

    /**
     * 修改商品数量
     * @param id integer id
     * @param number 修改的产品数量
     *  @author kepler edit by stivepeim 2020-7-4
     * @since 2020-05-28
     */
    @ApiOperation(value = "修改")
    @RequestMapping(value = "/num", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id, @RequestParam Integer number){
        if(null == number) throw new MallException("商品数量不合法");
        if(number <=0 || number > 99) throw new MallException("商品数量不能小于1大于99");
        StoreCart storeCart = storeCartService.getById(id);
        if(null == storeCart) throw new MallException("当前购物车不存在");
        storeCart.setId(Long.valueOf(id));
        storeCart.setCartNum(number);
        if(storeCartService.updateById(storeCart)){
            return CommonResult.success();
        }else{
            return CommonResult.failed();
        }
    }


    /**
     * 数量
     *  @author kepler
     * @since 2020-05-28
     */
    @ApiOperation(value = "数量")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public CommonResult<Map<Object, Object>> count(@RequestParam(value = "numType", defaultValue = "false") boolean numType ){
        Map<Object, Object> map = new HashMap<>();
        map.put("count", storeCartService.getUserCount(userService.getUserIdException(), "product",  numType));
        return CommonResult.success(map);
    }

    /**
     * 购物车重选提交
     * @param resetRequest 重选参数
     * @return 结果
     */
    @ApiOperation(value = "购物车重选提交")
    @RequestMapping(value = "/resetcart", method = RequestMethod.POST)
    public CommonResult<Object> resetCart(@RequestBody @Validated CartResetRequest resetRequest){
        return CommonResult.success(storeCartService.resetCart(resetRequest));
    }
}



