package com.jjsj.mall.validatecode.service;

import com.jjsj.mall.validatecode.model.ValidateCode;

/**
 * ValidateCodeService 接口

 */
public interface ValidateCodeService {

    ValidateCode get();

    boolean check(ValidateCode validateCode);
}