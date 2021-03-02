package com.jjsj.mall.front.service;

import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.front.response.IndexInfoResponse;
import com.jjsj.mall.front.response.IndexProductBannerResponse;
import java.util.HashMap;
import java.util.List;

/**
 * IndexService 接口 
 */
public interface IndexService {

    IndexProductBannerResponse getProductBanner(int type, PageParamRequest pageParamRequest);

    IndexInfoResponse getIndexInfo();

    List<HashMap<String, Object>> hotKeywords();

    HashMap<String, String> getShareConfig();

    /**
     * 获取公共配置
     *
     * @return 公共配置
     */
    HashMap<String, String> getCommConfig();
}
