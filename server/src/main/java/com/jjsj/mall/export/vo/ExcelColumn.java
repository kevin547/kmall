package com.jjsj.mall.export.vo;

import java.lang.annotation.*;

/** Excel 配置
 
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {

    String value() default "";

    int col() default 0;
}
