package com.jjsj.mall.task.product;

import com.jjsj.mall.bargain.service.StoreBargainService;
import com.jjsj.mall.combination.service.StoreCombinationService;
import com.jjsj.mall.seckill.service.StoreSeckillService;
import com.jjsj.mall.store.service.StoreProductService;
import com.jjsj.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 操作商品库存
 */

@Component
@Configuration //读取配置
@EnableScheduling // 2.开启定时任务
public class ProductStock {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(ProductStock.class);

    @Autowired
    private StoreProductService storeProductService;

    @Autowired
    private StoreSeckillService storeSeckillService;

    @Autowired
    private StoreBargainService storeBargainService;

    @Autowired
    private StoreCombinationService storeCombinationService;

    @Scheduled(fixedDelay = 1000 * 60L) //1分钟同步一次数据
    public void init() {
        logger.info(
            "---OrderTakeByUser task------produce Data with fixed rate task: Execution Time - {}",
            DateUtil.nowDateTime());
        try {
            storeProductService.consumeProductStock(); // 商品本身库存任务
            storeSeckillService.consumeProductStock(); // 秒杀本身库存任务
            storeBargainService.consumeProductStock(); // 砍价本身库存任务
            storeCombinationService.consumeProductStock(); // 拼团本身库存任务
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("OrderTakeByUser.task" + " | msg : " + e.getMessage());
        }

    }
}
