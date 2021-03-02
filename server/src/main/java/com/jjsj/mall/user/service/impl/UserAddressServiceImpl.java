package com.jjsj.mall.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjsj.common.PageParamRequest;
import com.jjsj.exception.MallException;
import com.github.pagehelper.PageHelper;
import com.jjsj.mall.front.request.UserAddressRequest;
import com.jjsj.mall.system.model.SystemCity;
import com.jjsj.mall.system.service.SystemCityService;
import com.jjsj.mall.user.dao.UserAddressDao;
import com.jjsj.mall.user.model.UserAddress;
import com.jjsj.mall.user.service.UserAddressService;
import com.jjsj.mall.user.service.UserService;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserAddressServiceImpl 接口实现
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressDao, UserAddress> implements
    UserAddressService {

    @Resource
    private UserAddressDao dao;

    @Autowired
    private SystemCityService systemCityService;

    @Autowired
    private UserService userService;

    /**
     * 列表
     *
     * @return List<UserAddress>
     *  @author kepler
     * @since 2020-04-28
     */
    @Override
    public List<UserAddress> getList(PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<UserAddress> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        return dao.selectList(lambdaQueryWrapper);
    }

    /**
     * 根据基本题条件查询
     *
     * @param address 查询条件
     * @return 结果地址
     */
    @Override
    public UserAddress getUserAddress(UserAddress address) {
        LambdaQueryWrapper lq = new LambdaQueryWrapper();
        lq.setEntity(address);
        return dao.selectOne(lq);
    }

    /**
     * 创建地址
     *
     * @param request UserAddressRequest 参数
     * @return List<UserAddress>
     *  @author kepler
     * @since 2020-04-28
     */
    @Override
    public UserAddress create(UserAddressRequest request) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(request, userAddress);
        userAddress.setCity(request.getAddress().getCity());
        userAddress.setCityId(request.getAddress().getCityId());
        userAddress.setDistrict(request.getAddress().getDistrict());
        userAddress.setProvince(request.getAddress().getProvince());

        if (request.getAddress().getCityId() > 0 && StringUtils
            .isNotBlank(request.getAddress().getCity())) {
            checkCity(userAddress.getCityId());
        }
        userAddress.setUid(userService.getUserIdException());
        if (userAddress.getIsDefault()) {
            //把当前用户其他默认地址取消
            cancelDefault(userAddress.getUid());
        }
        saveOrUpdate(userAddress);
        return userAddress;
    }

    /**
     * 设置默认
     *
     * @param id Integer id
     * @return UserAddress
     *  @author kepler
     * @since 2020-04-28
     */
    @Override
    public boolean def(Integer id) {
        //把当前用户其他默认地址取消
        cancelDefault(userService.getUserIdException());
        UserAddress userAddress = new UserAddress();
        userAddress.setId(id);
        userAddress.setUid(userService.getUserIdException());
        userAddress.setIsDefault(true);
        return updateById(userAddress);
    }

    /**
     * 删除
     *
     * @param id Integer id
     * @return UserAddress
     *  @author kepler
     * @since 2020-04-28
     */
    @Override
    public boolean delete(Integer id) {
        //把当前用户其他默认地址取消
        LambdaQueryWrapper<UserAddress> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAddress::getId, id);
        lambdaQueryWrapper.eq(UserAddress::getUid, userService.getUserIdException());
        dao.delete(lambdaQueryWrapper);
        return true;
    }

    /**
     * 获取默认地址
     *
     * @return UserAddress
     *  @author kepler
     * @since 2020-04-28
     */
    @Override
    public UserAddress getDefault() {
        //把当前用户其他默认地址取消
        LambdaQueryWrapper<UserAddress> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAddress::getIsDefault, true);
        lambdaQueryWrapper.eq(UserAddress::getUid, userService.getUserId());
        return dao.selectOne(lambdaQueryWrapper);
    }

    @Override
    public UserAddress getById(Integer addressId) {
        LambdaQueryWrapper<UserAddress> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAddress::getId, addressId);
        return dao.selectOne(lambdaQueryWrapper);
    }


    /**
     * 根据地址参数获取用户收货地址
     */
    @Override
    public List<UserAddress> getListByUserAddress(UserAddress userAddress,
        PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<UserAddress> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.setEntity(userAddress);
        return dao.selectList(lambdaQueryWrapper);
    }

    /**
     * 检测城市id是否合法
     *
     * @param cityId Integer 城市id
     *  @author kepler
     * @since 2020-04-30
     */
    private void checkCity(Integer cityId) {
        //检测城市Id是否存在
        SystemCity systemCity = systemCityService.getCityByCityId(cityId);
        if (systemCity == null) {
            throw new MallException("请选择正确的城市");
        }
    }

    /**
     * 取消默认地址
     *
     * @param userId Integer 城市id
     *  @author kepler
     * @since 2020-04-30
     */
    private void cancelDefault(Integer userId) {
        //检测城市Id是否存在
        UserAddress userAddress = new UserAddress();
        userAddress.setIsDefault(false);
        LambdaQueryWrapper<UserAddress> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAddress::getUid, userId);
        update(userAddress, lambdaQueryWrapper);
    }

}

