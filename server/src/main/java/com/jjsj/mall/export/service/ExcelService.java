package com.jjsj.mall.export.service;

import com.jjsj.mall.bargain.request.StoreBargainSearchRequest;
import com.jjsj.mall.combination.request.StoreCombinationSearchRequest;
import com.jjsj.mall.store.request.StoreProductSearchRequest;

import javax.servlet.http.HttpServletResponse;

/**
* StoreProductService 接口

*/
public interface ExcelService{
//    List<ProductExcelVo> product(StoreProductSearchRequest request, HttpServletResponse response);

    String exportBargainProduct(StoreBargainSearchRequest request, HttpServletResponse response);

    String exportCombinationProduct(StoreCombinationSearchRequest request, HttpServletResponse response);

    String exportProduct(StoreProductSearchRequest request, HttpServletResponse response);
}
