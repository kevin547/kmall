package com.jjsj.mall.front.service;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.Map;

/**
 * QrCodeService 接口

 */
public interface QrCodeService {

    Map<String, Object> get(JSONObject data) throws IOException;

    Map<String, Object> base64(String url);

    Map<String, Object> base64String(String text, int width, int height);

}
