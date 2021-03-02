package com.jjsj.mall.upload.service;

import com.qiniu.storage.UploadManager;
import com.jjsj.mall.upload.vo.CloudVo;

/**
 * QiNiuService 接口

 */
public interface QiNiuService {
    void upload(UploadManager uploadManager, CloudVo cloudVo, String upToken, String webPth, String localFile, Integer id);
}