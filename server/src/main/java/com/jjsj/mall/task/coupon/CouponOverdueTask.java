package com.jjsj.mall.task.coupon;

import com.jjsj.mall.marketing.service.StoreCouponUserService;
import com.jjsj.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 优惠券过期定时任务
 */
@Component
@Configuration //读取配置
@EnableScheduling // 2.开启定时任务
public class CouponOverdueTask {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(CouponOverdueTask.class);

    @Autowired
    private StoreCouponUserService couponUserService;

    @Scheduled(fixedDelay = 1000 * 60L) //1分钟同步一次数据
    public void init() {
        logger.info(
            "---CouponOverdueTask task------produce Data with fixed rate task: Execution Time - {}",
            DateUtil.nowDateTime());
        try {
            couponUserService.overdueTask();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("CouponOverdueTask.task" + " | msg : " + e.getMessage());
        }

    }

}
