package com.jjsj.mall.express.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.express.model.Express;
import com.jjsj.mall.express.request.ExpressSearchRequest;
import com.jjsj.mall.express.request.ExpressUpdateRequest;
import com.jjsj.mall.express.request.ExpressUpdateShowRequest;

import java.util.List;

/**
*  ExpressService 接口

*/
public interface ExpressService extends IService<Express> {

    /**
    * 列表
    * @param request 搜索条件
    * @param pageParamRequest 分页类参数
    *  @author kepler
    * @since 2020-04-17
    * @return List<Express>
    */
    List<Express> getList(ExpressSearchRequest request, PageParamRequest pageParamRequest);

    Express info(Integer id);

    /**
     * 编辑
     */
    Boolean updateExpress(ExpressUpdateRequest expressRequest);

    /**
     * 修改显示状态
     */
    Boolean updateExpressShow(ExpressUpdateShowRequest expressRequest);

    /**
     * 同步快递公司
     */
    Boolean syncExpress();

    /**
     * 查询全部快递公司
     * @param type 类型：normal-普通，elec-电子面单
     */
    List<Express> findAll(String type);

    /**
     * 查询快递公司面单模板
     * @param com 快递公司编号
     */
    JSONObject template(String com);

    /**
     * 查询快递公司
     * @param code 快递公司编号
     * @return
     */
    Express getByCode(String code);

    /**
     * 打印电子面单
     * @param cargo 物品名称
     * @param count 商品数量
     */
    Boolean dump(String cargo, Integer count);
}
