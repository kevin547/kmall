package com.jjsj.mall.front.controller;

import com.jjsj.common.CommonResult;
import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageInfo;
import com.jjsj.mall.combination.model.StoreCombination;
import com.jjsj.mall.combination.request.StorePinkRequest;
import com.jjsj.mall.combination.service.StoreCombinationService;
import com.jjsj.mall.front.response.CombinationDetailResponse;
import com.jjsj.mall.front.response.GoPinkResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 拼团商品
 
 */
@Slf4j
@RestController
@RequestMapping("api/front/combination")
@Api(tags = "拼团商品")
public class CombinationController {

    @Autowired
    private StoreCombinationService storeCombinationService;

    /**
     * 拼团Pink
     */
    @ApiOperation(value = "拼团Pink")
    @RequestMapping(value = "/pink", method = RequestMethod.GET)
    public CommonResult<HashMap<String,Object>> pink(){
        return CommonResult.success(storeCombinationService.getForH5Pink());
    }

    /**
     * 砍价商品列表
     * @return 砍价商品列表
     */
    @ApiOperation(value = "拼团商品列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<PageInfo<StoreCombination>> list(@ModelAttribute PageParamRequest pageParamRequest) {
        PageInfo<StoreCombination> h5List = storeCombinationService.getH5List(pageParamRequest);
        return CommonResult.success(h5List);
    }

    /**
     * 拼团商品详情
     * @return
     */
    @ApiOperation(value = "拼团商品详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public CommonResult<CombinationDetailResponse> detail(@PathVariable(value = "id") Integer id) {
        CombinationDetailResponse h5Detail = storeCombinationService.getH5Detail(id);
        return CommonResult.success(h5Detail);
    }

    /**
     * 去拼团
     * @param pinkId 拼团团长单id
     * @return
     */
    @ApiOperation(value = "去拼团")
    @RequestMapping(value = "/pink/{pinkId}", method = RequestMethod.GET)
    public CommonResult<GoPinkResponse> goPink(@PathVariable(value = "pinkId") Integer pinkId) {
        GoPinkResponse goPinkResponse = storeCombinationService.goPink(pinkId);
        return CommonResult.success(goPinkResponse);
    }

    /**
     * 更多拼团
     * @return
     */
    @ApiOperation(value = "更多拼团")
    @RequestMapping(value = "/more", method = RequestMethod.GET)
    public CommonResult<PageInfo<StoreCombination>> getMore(@RequestParam Integer comId, @Validated PageParamRequest pageParamRequest) {
        PageInfo<StoreCombination> more = storeCombinationService.getMore(pageParamRequest, comId);
        return CommonResult.success(more);
    }

    /**
     * 取消拼团
     * @return
     */
    @ApiOperation(value = "取消拼团")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonResult<Object> remove(@RequestBody @Validated StorePinkRequest storePinkRequest) {
        if (storeCombinationService.removePink(storePinkRequest)) {
            return CommonResult.success("取消成功");
        } else {
            return CommonResult.failed("取消失败");
        }
    }

    /**
     * 拼团海报
     */
//    @ApiOperation(value = "拼团海报")
//    @RequestMapping(value = "/poster", method = RequestMethod.POST)
//    public CommonResult<Object> poster(@Validated @RequestParam Integer pinkId, @Validated @RequestParam String from) {
//        if (storeCombinationService.poster(pinkId, from)) {
//            return CommonResult.success("取消成功");
//        } else {
//            return CommonResult.failed("取消失败");
//        }
//    }
}
