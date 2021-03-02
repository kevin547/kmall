package com.jjsj.mall.front.response;

import com.jjsj.mall.combination.response.StoreCombinationResponse;
import com.jjsj.mall.combination.response.StorePinkResponse;
import com.jjsj.mall.user.model.User;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 去拼团响应体 
 */
@Data
public class GoPinkResponse implements Serializable {

    private static final long serialVersionUID = 1962453930812558918L;

    /**
     * 拼团剩余人数
     **/
    private Integer count;

    /**
     * 当前拼团订单号
     **/
    private String currentPinkOrder;

    /**
     * 判断拼团是否完成
     **/
    private Integer isOk;

    /**
     * 团员列表
     **/
    private List<StorePinkResponse> pinkAll;

    /**
     * 拼团是否成功  0未成功 1成功
     **/
    private Integer pinkBool;

    /**
     * 当前用户是否在团内  0未在 1在
     **/
    private Integer userBool;

    /**
     * 团长信息
     **/
    private StorePinkResponse pinkT;

    /**
     * 当前拼团信息
     **/
    private StoreCombinationResponse storeCombination;

    /**
     * 当前用户信息
     **/
    private User userInfo;
}
