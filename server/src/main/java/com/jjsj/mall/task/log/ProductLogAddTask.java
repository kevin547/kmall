package com.jjsj.mall.task.log;

import com.jjsj.utils.DateUtil;
import com.jjsj.mall.log.service.StoreProductLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 日志记录task 
 */
@Component
@Configuration //读取配置
@EnableScheduling // 2.开启定时任务
public class ProductLogAddTask {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(ProductLogAddTask.class);

    @Autowired
    private StoreProductLogService logService;

    @Scheduled(fixedDelay = 1000 * 60L) // 一分钟同步一次数据
    public void init() {
        logger.info(
            "---ProductLogAddTask------produce Data with fixed rate task: Execution Time - {}",
            DateUtil.nowDate());
        try {
            logService.addLogTask();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ProductLogAddTask" + " | msg : " + e.getMessage());
        }

    }

}
