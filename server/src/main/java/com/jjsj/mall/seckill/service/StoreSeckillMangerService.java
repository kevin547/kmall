package com.jjsj.mall.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.seckill.response.StoreSeckillManagerResponse;
import com.jjsj.mall.seckill.model.StoreSeckillManger;
import com.jjsj.mall.seckill.request.StoreSeckillMangerRequest;
import com.jjsj.mall.seckill.request.StoreSeckillMangerSearchRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * StoreSeckillMangerService 接口

 */
public interface StoreSeckillMangerService extends IService<StoreSeckillManger> {

    List<StoreSeckillManagerResponse> getList(StoreSeckillManger request, PageParamRequest pageParamRequest);

    /**
     * 删除秒杀配置 逻辑删除
     * @param id 待删除id
     * @return  删除结果
     */
    boolean deleteLogicById(int id);

    /**
     * 检查时间段是否已经存在
     * @param storeSeckillManger    查询秒杀配置
     * @return  查询结果
     */
    List<StoreSeckillManger> checkTimeRangeUnique(StoreSeckillManger storeSeckillManger);

    /**
     * 更新秒杀配置
     * @param storeSeckillManger 待更新秒杀配置
     * @return  更新结果
     */
    boolean updateByCondition(StoreSeckillManger storeSeckillManger);

    /**
     * 详情
     * @param id 配置id
     * @return  查询到的结果
     */
    StoreSeckillManagerResponse detail(int id);

    /**
     * 获取正在秒杀的时间段
     * @return 正在秒杀的时间段
     */
    List<StoreSeckillManger> getCurrentSeckillManager();

    /**
     * 更新秒杀配置状态
     * @param id id
     * @param status 待更新状态
     * @return 结果
     */
    boolean updateStatus(int id, boolean status);

    /**
     * 更新秒杀配置
     * @param id id
     * @param storeSeckillMangerRequest 秒杀配置
     * @return 结果
     */
    boolean update(Integer id,StoreSeckillMangerRequest storeSeckillMangerRequest);
    void setTimeRangeFromRequest(@Validated @RequestBody StoreSeckillMangerRequest storeSeckillMangerRequest, StoreSeckillManger storeSeckillManger);
    void setTimeRangeFromRequest(@Validated @RequestBody StoreSeckillMangerSearchRequest request, StoreSeckillManger storeSeckillManger);
}
