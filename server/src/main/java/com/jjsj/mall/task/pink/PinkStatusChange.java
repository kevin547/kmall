package com.jjsj.mall.task.pink;

import com.jjsj.mall.combination.service.StorePinkService;
import com.jjsj.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 拼团状态变化Task
 */
@Component
@Configuration //读取配置
@EnableScheduling // 2.开启定时任务
public class PinkStatusChange {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(PinkStatusChange.class);

    @Autowired
    private StorePinkService storePinkService;

    @Scheduled(cron = "0 */1 * * * ?") //每分钟执行一次
    public void init() {
        logger.info("---PinkStatusChange------bargain stop status change task: Execution Time - {}",
            DateUtil.nowDateTime());
        try {
            storePinkService.detectionStatus();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("PinkStatusChange" + " | msg : " + e.getMessage());
        }

    }
}
