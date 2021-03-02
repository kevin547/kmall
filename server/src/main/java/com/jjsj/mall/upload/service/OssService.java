package com.jjsj.mall.upload.service;

import com.jjsj.mall.upload.vo.CloudVo;

/**
 * OssService 接口

 */
public interface OssService {
    void upload(CloudVo cloudVo, String webPth, String localFile, Integer id);
}