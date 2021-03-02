package com.jjsj.mall.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jjsj.mall.front.response.UserSpreadPeopleItemResponse;
import com.jjsj.mall.user.model.User;
import com.jjsj.mall.user.vo.UserOperateFundsVo;
import java.util.List;
import java.util.Map;

/**
 * 用户表 Mapper 接口
 */
public interface UserDao extends BaseMapper<User> {

    Boolean updateFounds(UserOperateFundsVo userOperateFundsVo);

    List<UserSpreadPeopleItemResponse> getSpreadPeopleList(Map<String, Object> map);
}
