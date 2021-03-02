package com.jjsj.mall.store.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * StoreCategoryTreeList

 */
@Data
public class StoreCategoryTreeList {
    private static final long serialVersionUID=1L;

    private Integer id;

    private Integer pid;

    private String cateName;

    private Integer sort;

    private String pic;

    private Boolean isShow;

    private Integer addTime;

    private List<StoreCategoryTreeList> child = new ArrayList<>();
}
