package com.jjsj.mall.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjsj.constants.Constants;
import com.jjsj.exception.MallException;
import com.jjsj.mall.user.dao.UserTokenDao;
import com.jjsj.mall.user.model.User;
import com.jjsj.mall.user.model.UserToken;
import com.jjsj.mall.user.service.UserService;
import com.jjsj.mall.user.service.UserTokenService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserTokenServiceImpl 接口实现
 */
@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenDao, UserToken> implements
    UserTokenService {

    @Resource
    private UserTokenDao dao;

    @Autowired
    private UserService userService;

    /**
     * 检测token是否存在
     *
     * @param token String openId
     * @param type int 类型
     * @return UserToken
     *  @author kepler
     * @since 2020-05-25
     */
    @Override
    public UserToken checkToken(String token, int type) {
        LambdaQueryWrapper<UserToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserToken::getType, type).eq(UserToken::getToken, token);
        return dao.selectOne(lambdaQueryWrapper);
    }

    /**
     * 绑定token关系
     *
     * @param token String token
     * @param type int 类型
     *  @author kepler
     * @since 2020-05-25
     */
    @Override
    public void bind(String token, int type, Integer userId) {
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setType(type);
        userToken.setUid(userId);
        save(userToken);
    }

    /**
     * 解绑微信
     *
     * @return Boolean
     *  @author kepler
     * @since 2020-04-28
     */
    @Override
    public Boolean unBind(int type, Integer uid) {
        try {
            LambdaQueryWrapper<UserToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(UserToken::getUid, uid).eq(UserToken::getType, type);
            dao.delete(lambdaQueryWrapper);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserToken getTokenByUserId(Integer userId, int type) {
        LambdaQueryWrapper<UserToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserToken::getUid, userId).eq(UserToken::getType, type);
        return dao.selectOne(lambdaQueryWrapper);
    }

    @Override
    public UserToken getTokenByUserIdException(Integer userId, int type) {
        UserToken userToken = getTokenByUserId(userId, type);
        if (null == userToken) {
            throw new MallException("没有找到和用户相关的微信数据");
        }

        User user = userService.getById(userId);

        if (null != user.getSubscribe() && !user.getSubscribe()) {
            throw new MallException("此用户没有关注公众号");
        }

        return userToken;
    }

    @Override
    public List<UserToken> getList(List<Integer> userIdList) {
        LambdaQueryWrapper<UserToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(UserToken::getUid, userIdList)
            .eq(UserToken::getType, Constants.PAY_TYPE_WE_CHAT_FROM_PUBLIC);
        return dao.selectList(lambdaQueryWrapper);
    }

    @Override
    public UserToken getUserIdByOpenId(String openid, int type) {
        LambdaQueryWrapper<UserToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserToken::getToken, openid);
        lambdaQueryWrapper.eq(UserToken::getType, type);
        return dao.selectOne(lambdaQueryWrapper);
    }

    @Override
    public UserToken getByOpenid(String openid) {
        LambdaQueryWrapper<UserToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserToken::getToken, openid);
        return dao.selectOne(lambdaQueryWrapper);
    }

    @Override
    public UserToken getByUid(Integer uid) {
        LambdaQueryWrapper<UserToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserToken::getUid, uid);
        return dao.selectOne(lambdaQueryWrapper);
    }
}

