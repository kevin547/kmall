package com.jjsj.mall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.sms.model.SmsRecord;
import com.jjsj.mall.sms.request.SmsRecordRequest;
import java.util.List;

/**
 * SmsRecordService 接口
 */
public interface SmsRecordService extends IService<SmsRecord> {

    /**
     * 列表
     *
     * @param request 请求参数
     * @param pageParamRequest 分页类参数
     * @return List<SmsRecord>
     *  @author kepler
     * @since 2020-04-16
     */
    List<SmsRecord> getList(SmsRecordRequest request, PageParamRequest pageParamRequest);

    /**
     * 保存短信记录
     *
     * @param smsRecord 待保存短信记录
     * @return 保存结果
     */
    boolean save(SmsRecord smsRecord);

}
