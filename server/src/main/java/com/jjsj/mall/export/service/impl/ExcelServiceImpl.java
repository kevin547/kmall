package com.jjsj.mall.export.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.jjsj.common.PageParamRequest;
import com.jjsj.constants.Constants;
import com.jjsj.exception.MallException;
import com.github.pagehelper.PageInfo;
import com.jjsj.mall.bargain.request.StoreBargainSearchRequest;
import com.jjsj.mall.bargain.response.StoreBargainResponse;
import com.jjsj.mall.bargain.service.StoreBargainService;
import com.jjsj.mall.category.service.CategoryService;
import com.jjsj.mall.combination.request.StoreCombinationSearchRequest;
import com.jjsj.mall.combination.response.StoreCombinationResponse;
import com.jjsj.mall.combination.service.StoreCombinationService;
import com.jjsj.mall.system.service.SystemConfigService;
import com.jjsj.utils.MallUtil;
import com.jjsj.utils.DateUtil;
import com.jjsj.utils.ExportUtil;
import com.jjsj.mall.export.service.ExcelService;
import com.jjsj.mall.export.vo.BargainProductExcelVo;
import com.jjsj.mall.export.vo.CombinationProductExcelVo;
import com.jjsj.mall.export.vo.ProductExcelVo;
import com.jjsj.mall.store.request.StoreProductSearchRequest;
import com.jjsj.mall.store.response.StoreProductResponse;
import com.jjsj.mall.store.service.StoreProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
*  ExcelServiceImpl 接口实现

*/
@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private StoreProductService storeProductService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StoreBargainService storeBargainService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private StoreCombinationService storeCombinationService;

    /**
     * 导出砍价商品
     * @param request
     * @param response
     * @return
     */
    @Override
    public String exportBargainProduct(StoreBargainSearchRequest request, HttpServletResponse response) {
        PageParamRequest pageParamRequest = new PageParamRequest();
        pageParamRequest.setPage(Constants.DEFAULT_PAGE);
        pageParamRequest.setLimit(Constants.EXPORT_MAX_LIMIT);
        PageInfo<StoreBargainResponse> page = storeBargainService.getList(request, pageParamRequest);
        if (CollUtil.isEmpty(page.getList())) throw new MallException("没有可导出的数据!");
        List<StoreBargainResponse> list = page.getList();
        List<BargainProductExcelVo> voList = list.stream().map(temp -> {
            BargainProductExcelVo vo = new BargainProductExcelVo();
            BeanUtils.copyProperties(temp, vo);
            vo.setPrice("￥".concat(temp.getPrice().toString()));
            vo.setStatus(temp.getStatus() ? "开启" : "关闭");
            vo.setStartTime(temp.getStartTime());
            vo.setStopTime(temp.getStopTime());
            vo.setAddTime(temp.getAddTime());
            return vo;
        }).collect(Collectors.toList());

        // 上传设置
        ExportUtil.setUpload(systemConfigService.getValueByKey(Constants.UPLOAD_ROOT_PATH_CONFIG_KEY), Constants.UPLOAD_MODEL_PATH_EXCEL, Constants.UPLOAD_TYPE_FILE);

        // 文件名
        String fileName = "砍价".concat(DateUtil.nowDateTime(Constants.DATE_TIME_FORMAT_NUM)).concat(
            MallUtil.randomCount(111111111, 999999999).toString()).concat(".xlsx");

        //自定义标题别名
        LinkedHashMap<String, String> aliasMap = new LinkedHashMap<>();
        aliasMap.put("title", "砍价活动名称");
        aliasMap.put("info", "砍价活动简介");
        aliasMap.put("price", "砍价金额");
        aliasMap.put("bargainNum", "用户每次砍价的次数");
        aliasMap.put("status", "砍价状态");
        aliasMap.put("startTime", "砍价开启时间");
        aliasMap.put("stopTime", "砍价结束时间");
        aliasMap.put("sales", "销量");
        aliasMap.put("quotaShow", "库存");
        aliasMap.put("giveIntegral", "返多少积分");
        aliasMap.put("addTime", "添加时间");

        return ExportUtil.exportExecl(fileName, "砍价商品导出", voList, aliasMap);
    }

    /**
     * 导出拼团商品
     * @param request
     * @param response
     * @return
     */
    @Override
    public String exportCombinationProduct(StoreCombinationSearchRequest request, HttpServletResponse response) {
        PageParamRequest pageParamRequest = new PageParamRequest();
        pageParamRequest.setPage(Constants.DEFAULT_PAGE);
        pageParamRequest.setLimit(Constants.EXPORT_MAX_LIMIT);
        PageInfo<StoreCombinationResponse> page = storeCombinationService.getList(request, pageParamRequest);
        if (CollUtil.isEmpty(page.getList())) throw new MallException("没有可导出的数据!");
        List<StoreCombinationResponse> list = page.getList();
        List<CombinationProductExcelVo> voList = list.stream().map(temp -> {
            CombinationProductExcelVo vo = new CombinationProductExcelVo();
            BeanUtils.copyProperties(temp, vo);
            vo.setIsShow(temp.getIsShow() ? "开启" : "关闭");
            vo.setStopTime(DateUtil.timestamp2DateStr(temp.getStopTime(), Constants.DATE_FORMAT_DATE));
            return vo;
        }).collect(Collectors.toList());

        // 上传设置
        ExportUtil.setUpload(systemConfigService.getValueByKey(Constants.UPLOAD_ROOT_PATH_CONFIG_KEY), Constants.UPLOAD_MODEL_PATH_EXCEL, Constants.UPLOAD_TYPE_FILE);

        // 文件名
        String fileName = "拼团".concat(DateUtil.nowDateTime(Constants.DATE_TIME_FORMAT_NUM)).concat(
            MallUtil.randomCount(111111111, 999999999).toString()).concat(".xlsx");

        //自定义标题别名
        LinkedHashMap<String, String> aliasMap = new LinkedHashMap<>();
        aliasMap.put("id", "编号");
        aliasMap.put("title", "拼团名称");
        aliasMap.put("otPrice", "原价");
        aliasMap.put("price", "拼团价");
        aliasMap.put("quotaShow", "库存");
        aliasMap.put("countPeople", "拼团人数");
        aliasMap.put("countPeopleAll", "参与人数");
        aliasMap.put("countPeoplePink", "成团数量");
        aliasMap.put("sales", "销量");
        aliasMap.put("isShow", "商品状态");
        aliasMap.put("stopTime", "拼团结束时间");

        return ExportUtil.exportExecl(fileName, "拼团商品导出", voList, aliasMap);
    }

    /**
     * 商品导出
     * @param request
     * @param response
     * @return
     */
    @Override
    public String exportProduct(StoreProductSearchRequest request, HttpServletResponse response) {
        PageParamRequest pageParamRequest = new PageParamRequest();
        pageParamRequest.setPage(Constants.DEFAULT_PAGE);
        pageParamRequest.setLimit(Constants.EXPORT_MAX_LIMIT);
        PageInfo<StoreProductResponse> storeProductResponsePageInfo = storeProductService.getList(request, pageParamRequest);
        List<StoreProductResponse> list = storeProductResponsePageInfo.getList();
        if(list.size() < 1){
            throw new MallException("没有可导出的数据！");
        }

        //产品分类id
        List<String> cateIdListStr = list.stream().map(StoreProductResponse::getCateId).distinct().collect(Collectors.toList());

        HashMap<Integer, String> categoryNameList = new HashMap<Integer, String>();
        if(cateIdListStr.size() > 0){
            String join = StringUtils.join(cateIdListStr, ",");
            List<Integer> cateIdList = MallUtil.stringToArray(join);
            categoryNameList = categoryService.getListInId(cateIdList);
        }
        List<ProductExcelVo> voList = CollUtil.newArrayList();
        for (StoreProductResponse product : list ) {
            ProductExcelVo vo = new ProductExcelVo();
            vo.setStoreName(product.getStoreName());
            vo.setStoreInfo(product.getStoreInfo());
            vo.setCateName(MallUtil.getValueByIndex(categoryNameList, product.getCateId()));
            vo.setPrice("￥" + product.getPrice());
            vo.setStock(product.getStock().toString());
            vo.setSales(product.getSales().toString());
            vo.setBrowse(product.getBrowse().toString());
            voList.add(vo);
        }

        /**
         * ===============================
         * 以下为存储部分
         * ===============================
         */
        // 上传设置
        ExportUtil.setUpload(systemConfigService.getValueByKey(Constants.UPLOAD_ROOT_PATH_CONFIG_KEY), Constants.UPLOAD_MODEL_PATH_EXCEL, Constants.UPLOAD_TYPE_FILE);

        // 文件名
        String fileName = "商品导出_".concat(DateUtil.nowDateTime(Constants.DATE_TIME_FORMAT_NUM)).concat(
            MallUtil.randomCount(111111111, 999999999).toString()).concat(".xlsx");

        //自定义标题别名
        LinkedHashMap<String, String> aliasMap = new LinkedHashMap<>();
        aliasMap.put("storeName", "商品名称");
        aliasMap.put("storeInfo", "商品简介");
        aliasMap.put("cateName", "商品分类");
        aliasMap.put("price", "价格");
        aliasMap.put("stock", "库存");
        aliasMap.put("sales", "销量");
        aliasMap.put("browse", "浏览量");

        return ExportUtil.exportExecl(fileName, "商品导出", voList, aliasMap);
    }
}

