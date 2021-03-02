package com.jjsj.mall.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjsj.common.PageParamRequest;
import com.jjsj.constants.Constants;
import com.jjsj.exception.MallException;
import com.github.pagehelper.PageHelper;
import com.jjsj.mall.system.model.SystemUserLevel;
import com.jjsj.mall.system.request.SystemUserLevelSearchRequest;
import com.jjsj.mall.system.service.SystemUserLevelService;
import com.jjsj.utils.DateUtil;
import com.jjsj.mall.user.dao.UserLevelDao;
import com.jjsj.mall.user.model.User;
import com.jjsj.mall.user.model.UserLevel;
import com.jjsj.mall.user.request.UserLevelSearchRequest;
import com.jjsj.mall.user.service.UserLevelService;
import com.jjsj.mall.user.service.UserService;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * UserLevelServiceImpl 接口实现
 */
@Service
public class UserLevelServiceImpl extends ServiceImpl<UserLevelDao, UserLevel> implements
    UserLevelService {

    @Resource
    private UserLevelDao dao;

    @Autowired
    private SystemUserLevelService systemUserLevelService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionTemplate transactionTemplate;


    /**
     * 列表
     *
     * @param request 请求参数
     * @param pageParamRequest 分页类参数
     * @return List<UserLevel>
     *  @author kepler
     * @since 2020-04-28
     */
    @Override
    public List<UserLevel> getList(UserLevelSearchRequest request,
        PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        return dao.selectList(null);
    }

    /**
     * 修改用户等级
     *
     * @param userId integer id
     * @param levelId integer 等级
     * @return Boolean
     *  @author kepler
     * @since 2020-04-10
     */
    @Override
    public boolean level(Integer userId, int levelId) {
        SystemUserLevel systemUserLevel = systemUserLevelService.getById(levelId);
        User user = userService.getById(userId);
        UserLevel userLevelVo = checkUserLevel(userId, levelId, systemUserLevel);

        UserLevel userLevel = new UserLevel();
        userLevel.setStatus(true);
        userLevel.setIsDel(false);
        userLevel.setGrade(systemUserLevel.getGrade());
        userLevel.setUid(userId);
        userLevel.setLevelId(levelId);
        userLevel.setDiscount(systemUserLevel.getDiscount());

        Date date = DateUtil.nowDateTimeReturnDate(Constants.DATE_FORMAT);
        String mark = Constants.USER_LEVEL_OPERATE_LOG_MARK
            .replace("【{$userName}】", user.getNickname()).
                replace("{$date}", DateUtil.dateToStr(date, Constants.DATE_FORMAT)).
                replace("{$levelName}", systemUserLevel.getName());
        userLevel.setMark(mark);

        if (userLevelVo == null) {
            //创建新的会员等级信息
            save(userLevel);
        } else {
            //有数据，更新即可
            userLevel.setId(userLevelVo.getId());
            updateById(userLevel);
        }

        //更新会员等级
        user.setLevel(systemUserLevel.getGrade());
        userService.updateById(user);
        return true;
    }


    /**
     * 获取会员的等级信息，并且验证
     *
     * @param userId integer id
     * @param levelId integer 等级
     * @return Boolean
     *  @author kepler
     * @since 2020-04-10
     */
    private UserLevel checkUserLevel(Integer userId, Integer levelId,
        SystemUserLevel systemUserLevel) {
        //查询等级是否存在
        if (systemUserLevel == null) {
            throw new MallException("当前会员等级不存在");
        }

        if (systemUserLevel.getIsDel()) {
            throw new MallException("当前会员等级已删除");
        }

        //当前用户会员是否过期
        return getUserLevel(userId, levelId);
    }

    /**
     * 获取会员的等级信息
     *
     * @param userId integer id
     * @return Boolean
     *  @author kepler
     * @since 2020-04-10
     */
    private UserLevel getUserLevel(Integer userId, Integer levelId) {
        LambdaQueryWrapper<UserLevel> levelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        levelLambdaQueryWrapper.eq(UserLevel::getUid, userId);
        levelLambdaQueryWrapper.eq(UserLevel::getLevelId, levelId);
        levelLambdaQueryWrapper.eq(UserLevel::getIsDel, 0);
        return dao.selectOne(levelLambdaQueryWrapper);
    }

    /**
     * 根据用户id获取用户等级信息
     *
     * @param userId 用户id
     * @return 用户等级
     */
    @Override
    public UserLevel getUserLevelByUserId(Integer userId) {
        LambdaQueryWrapper<UserLevel> levelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        levelLambdaQueryWrapper.eq(UserLevel::getUid, userId);
        levelLambdaQueryWrapper.eq(UserLevel::getIsDel, 0);
        return dao.selectOne(levelLambdaQueryWrapper);
    }

    /**
     * 用户升级
     */
    @Override
    public Boolean upLevel(User user) {
        //确定当前经验所达到的等级
        SystemUserLevelSearchRequest systemUserLevelSearchRequest = new SystemUserLevelSearchRequest();
        systemUserLevelSearchRequest.setIsDel(false);
        systemUserLevelSearchRequest.setIsShow(true);
        List<SystemUserLevel> list = systemUserLevelService
            .getList(systemUserLevelSearchRequest, new PageParamRequest());

        SystemUserLevel userLevelConfig = new SystemUserLevel();
        for (SystemUserLevel systemUserLevel : list) {
            if (user.getExperience() > systemUserLevel.getExperience()) {
                userLevelConfig = systemUserLevel;
                continue;
            }
            break;
        }

        if (userLevelConfig.getId() == null) {
            System.out.println("未找到用户对应的等级");
            return Boolean.TRUE;
        }

        // 判断用户是否还在原等级
        UserLevel userLevel = getByUid(user.getUid());
        if (ObjectUtil.isNotNull(userLevel) && userLevelConfig.getId()
            .equals(userLevel.getLevelId())) {
            // 之前有记录，并且等级不需要变化
            return Boolean.TRUE;
        }

        UserLevel newLevel = new UserLevel();
        newLevel.setStatus(true);
        newLevel.setIsDel(false);
        newLevel.setGrade(userLevelConfig.getGrade());
        newLevel.setUid(user.getUid());
        newLevel.setLevelId(userLevelConfig.getId());
        newLevel.setDiscount(userLevelConfig.getDiscount());

        Date date = DateUtil.nowDateTimeReturnDate(Constants.DATE_FORMAT);
        String mark = Constants.USER_LEVEL_OPERATE_LOG_MARK
            .replace("【{$userName}】", user.getNickname()).
                replace("{$date}", DateUtil.dateToStr(date, Constants.DATE_FORMAT)).
                replace("{$levelName}", userLevelConfig.getName());
        newLevel.setMark(mark);

        //更新会员等级
        user.setLevel(userLevelConfig.getGrade());
        Boolean execute = transactionTemplate.execute(e -> {
            if (userLevel == null) {
                //创建新的会员等级信息
                save(newLevel);
            } else {
                //有数据，更新即可
                newLevel.setId(userLevel.getId());
                updateById(newLevel);
                // 将原等级删除
                userLevel.setIsDel(true);
                updateById(userLevel);
            }

            userService.updateById(user);
            return Boolean.TRUE;
        });
        return execute;
    }

    private UserLevel getByUid(Integer uid) {
        LambdaQueryWrapper<UserLevel> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserLevel::getUid, uid);
        lqw.eq(UserLevel::getStatus, true);
        lqw.eq(UserLevel::getIsDel, false);
        return dao.selectOne(lqw);
    }
}

