package com.jjsj.mall.upload.service;

import com.jjsj.mall.system.model.SystemAttachment;
import java.util.List;

/**
 * AsyncService 接口

 */
public interface AsyncService {
    void async(List<SystemAttachment> systemAttachmentList);

    String getCurrentBaseUrl();
}