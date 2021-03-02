package com.jjsj.mall.wechat.service.impl;

import com.jjsj.common.PageParamRequest;
import com.github.pagehelper.PageHelper;

import com.jjsj.mall.wechat.model.WechatQrcode;
import com.jjsj.mall.wechat.dao.WechatQrcodeDao;
import com.jjsj.mall.wechat.request.WechatQrcodeSearchRequest;
import com.jjsj.mall.wechat.service.WechatQrcodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * WechatQrcodeServiceImpl 接口实现

 */
@Service
public class WechatQrcodeServiceImpl extends ServiceImpl<WechatQrcodeDao, WechatQrcode> implements WechatQrcodeService {

    @Resource
    private WechatQrcodeDao dao;


    /**
    * 列表
    * @param request 请求参数
    * @param pageParamRequest 分页类参数
    *  @author kepler
    * @since 2020-04-18
    * @return List<WechatQrcode>
    */
    @Override
    public List<WechatQrcode> getList(WechatQrcodeSearchRequest request, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        return dao.selectList(null);
    }

}

