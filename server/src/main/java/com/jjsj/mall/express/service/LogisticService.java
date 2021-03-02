package com.jjsj.mall.express.service;

import com.jjsj.mall.express.vo.LogisticsResultVo;

/**
* ExpressService 接口

*/
public interface LogisticService {
    LogisticsResultVo info(String expressNo, String type, String com, String phone);
}
