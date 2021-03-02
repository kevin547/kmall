package com.jjsj.mall.wechat.controller;

import com.jjsj.common.CommonResult;
import com.jjsj.mall.wechat.service.WechatUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * 微信用户表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/wechat/user")
@Api(tags = "微信开放平台 -- 微信用户")
public class WechatUserController {

    @Autowired
    private WechatUserService wechatUserService;

    /**
     * 消息推送
     * @param userId 用户id
     * @param newsId 图文消息id
     *  @author kepler
     * @since 2020-04-11
     */
    @ApiOperation(value = "消息推送")
    @RequestMapping(value = "/push", method = RequestMethod.GET)
    public CommonResult<Boolean> info(@RequestParam(value = "userId") String userId,
                                         @RequestParam(value = "newsId") Integer newsId){
        wechatUserService.push(userId, newsId);
        return CommonResult.success();
    }
}



