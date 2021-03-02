package com.jjsj.mall.task.cloud;

import com.jjsj.mall.system.service.SystemAttachmentService;
import com.jjsj.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 云服务同步任务
 */
//@Component
//@Configuration //读取配置
//@EnableScheduling // 2.开启定时任务
public class AsyncCloud {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(AsyncCloud.class);

    @Autowired
    private SystemAttachmentService systemAttachmentService;

    @Scheduled(fixedDelay = 1000 * 5L) //5秒钟同步一次数据
    public void init() {
        logger
            .info("---AsyncCloud task------produce Data with fixed rate task: Execution Time - {}",
                DateUtil.nowDateTime());
        try {
            systemAttachmentService.async();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("AsyncCloud.task" + " | msg : " + e.getMessage());
        }

    }
}
