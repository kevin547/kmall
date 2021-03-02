package com.jjsj.mall.task.wechat;

import com.jjsj.mall.wechat.service.WechatProgramPublicTempService;
import com.jjsj.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 小程序公共模板库
 */
//@Component
//@Configuration //读取配置
//@EnableScheduling // 2.开启定时任务
public class AsyncWeChatProgramTempList {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(AsyncWeChatProgramTempList.class);

    @Autowired
    private WechatProgramPublicTempService wechatProgramPublicTempService;

    @Value("${server.asyncWeChatProgramTempList}")
    private Boolean asyncWeChatProgramTempList;

    @Scheduled(fixedDelay = 1000L * 60 * 60 * 24) //1天同步一次数据
    public void init() {
        logger.info(
            "---AsyncWeChatProgramTempList task------produce Data with fixed rate task: Execution Time - {}",
            DateUtil.nowDate());
        try {
            if (asyncWeChatProgramTempList) {
                wechatProgramPublicTempService.async();
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("AsyncWeChatProgramTempList.task" + " | msg : " + e.getMessage());
        }

    }
}
