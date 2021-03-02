package com.jjsj.mall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.mall.user.model.UserToken;
import java.util.List;

/**
 * UserTokenService 接口实现
 */
public interface UserTokenService extends IService<UserToken> {

    UserToken checkToken(String token, int type);

    void bind(String openId, int type, Integer uid);

    Boolean unBind(int type, Integer uid);

    UserToken getTokenByUserId(Integer userId, int type);

    UserToken getTokenByUserIdException(Integer userId, int type);

    List<UserToken> getList(List<Integer> userIdList);

    UserToken getUserIdByOpenId(String openid, int type);

    UserToken getByOpenid(String openid);

    UserToken getByUid(Integer uid);
}