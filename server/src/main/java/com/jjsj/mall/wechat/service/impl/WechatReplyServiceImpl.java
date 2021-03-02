package com.jjsj.mall.wechat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jjsj.common.PageParamRequest;
import com.jjsj.exception.MallException;
import com.github.pagehelper.PageHelper;

import com.jjsj.mall.system.service.SystemAttachmentService;
import com.jjsj.mall.wechat.model.WechatReply;
import com.jjsj.mall.wechat.dao.WechatReplyDao;
import com.jjsj.mall.wechat.request.WechatReplySearchRequest;
import com.jjsj.mall.wechat.service.WechatReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * WechatReplyServiceImpl 接口实现

 */
@Service
public class WechatReplyServiceImpl extends ServiceImpl<WechatReplyDao, WechatReply> implements WechatReplyService {

    @Resource
    private WechatReplyDao dao;

    @Autowired
    private SystemAttachmentService systemAttachmentService;


    /**
    * 列表
    * @param request 请求参数
    * @param pageParamRequest 分页类参数
    *  @author kepler
    * @since 2020-04-18
    * @return List<WechatReply>
    */
    @Override
    public List<WechatReply> getList(WechatReplySearchRequest request, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<WechatReply> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        //类型
        if(StringUtils.isNotBlank(request.getType())){
            lambdaQueryWrapper.eq(WechatReply::getType, request.getType());
        }

        //关键字
        if(StringUtils.isNotBlank(request.getKeywords())){
            lambdaQueryWrapper.eq(WechatReply::getKeywords, request.getKeywords());
        }
        lambdaQueryWrapper.orderByDesc(WechatReply::getId);
        return dao.selectList(lambdaQueryWrapper);
    }

    /**
     * 新增微信关键字回复表
     * @param wechatReply 新增参数
     *  @author kepler
     * @since 2020-04-18
     */
    @Override
    public Boolean create(WechatReply wechatReply) {
        //检测重复
        WechatReply voByKeywords = getVoByKeywords(wechatReply.getKeywords());
        wechatReply.setData(systemAttachmentService.clearPrefix(wechatReply.getData()));
        if(voByKeywords != null){
            throw new MallException(wechatReply.getKeywords() + "关键字已经存在");
        }
        return save(wechatReply);
    }

    /**
     * 新增微信关键字回复表
     * @param wechatReply 新增参数
     *  @author kepler
     * @since 2020-04-18
     */
    @Override
    public Boolean updateVo(WechatReply wechatReply) {
        //检测重复
        WechatReply voByKeywords = getVoByKeywords(wechatReply.getKeywords());

        if(voByKeywords != null && !wechatReply.getId().equals(voByKeywords.getId())){
            throw new MallException(wechatReply.getKeywords() + "关键字已经存在");
        }

        wechatReply.setData(systemAttachmentService.clearPrefix(wechatReply.getData()));
        updateById(wechatReply);
        return true;
    }


    /**
     * 根据关键字查询数据
     * @param keywords 新增参数
     *  @author kepler
     * @since 2020-04-18
     * @return WechatReply
     */
    @Override
    public WechatReply getVoByKeywords(String keywords) {
        //检测重复
        LambdaQueryWrapper<WechatReply> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(WechatReply::getKeywords, keywords);
        WechatReply wechatReply = dao.selectOne(objectLambdaQueryWrapper);
        if(null == wechatReply){
            return null;
        }

        return wechatReply;
    }

    /**
     * 根据关键字查询数据
     * @param id Integer id
     *  @author kepler
     * @since 2020-04-18
     * @return WechatReply
     */
    @Override
    public WechatReply getInfoException(Integer id, boolean isTrue) {
        //检测重复
        WechatReply info = getInfo(id);
        if(null == info){
            throw new MallException("没有找到相关数据");
        }

        if(!info.getStatus() && isTrue){
            throw new MallException("没有找到相关数据");
        }

        return info;
    }

    /**
     * 根据关键字查询数据
     * @param id Integer id
     *  @author kepler
     * @since 2020-04-18
     * @return WechatReply
     */
    @Override
    public WechatReply getInfo(Integer id) {
       return getById(id);
    }

}

