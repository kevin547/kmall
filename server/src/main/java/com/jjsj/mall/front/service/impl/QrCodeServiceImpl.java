package com.jjsj.mall.front.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jjsj.exception.MallException;
import com.jjsj.mall.wechat.service.WeChatService;
import com.jjsj.utils.MallUtil;
import com.jjsj.utils.QRCodeUtil;
import com.jjsj.utils.RestTemplateUtil;
import com.jjsj.mall.front.service.QrCodeService;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * QrCodeServiceImpl 接口实现
 */
@Service
public class QrCodeServiceImpl implements QrCodeService {

    @Autowired
    private WeChatService weChatService;

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    /**
     * 二维码
     *
     * @return Object
     *  @author kepler
     * @since 2020-05-25
     */
    @Override
    public Map<String, Object> get(JSONObject data) {
        Map<String, Object> map = new HashMap<>();
        StringBuilder scene = new StringBuilder();
        String page = "";
        try {
            if (null != data) {
                Map<Object, Object> dataMap = JSONObject.toJavaObject(data, Map.class);

                for (Map.Entry<Object, Object> m : dataMap.entrySet()) {
                    if (m.getKey().equals("path")) {
                        //前端路由， 不需要拼参数
                        page = m.getValue().toString();
                        continue;
                    }
                    scene.append(m.getKey()).append("=").append(m.getValue()).append("&");
                }
            }
        } catch (Exception e) {
            throw new MallException("url参数错误 " + e.getMessage());
        }
        String uri = "";
        if (StringUtils.isNotBlank(scene)) {
            uri = scene.substring(0, scene.length() - 1);
        }

        map.put("code", weChatService.qrCode(page, uri));
        return map;
    }

    @Override
    public Map<String, Object> base64(String url) {
        byte[] bytes = restTemplateUtil.getBuffer(url);
        String base64Image = MallUtil.getBase64Image(Base64.encodeBase64String(bytes));
        Map<String, Object> map = new HashMap<>();
        map.put("code", base64Image);
        return map;
    }

    /**
     * 讲字符串转为QRcode
     *
     * @param text 待转换字符串
     * @return QRcode base64格式
     */
    @Override
    public Map<String, Object> base64String(String text, int width, int height) {

        String base64Image = null;
        try {
            base64Image = QRCodeUtil.crateQRCode(text, width, height);
        } catch (Exception e) {
            throw new MallException("生成二维码异常");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", base64Image);
        return map;
    }
}

