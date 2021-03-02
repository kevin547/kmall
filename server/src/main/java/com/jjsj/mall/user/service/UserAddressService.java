package com.jjsj.mall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjsj.common.PageParamRequest;
import com.jjsj.mall.front.request.UserAddressRequest;
import com.jjsj.mall.user.model.UserAddress;
import java.util.List;

/**
 * UserAddressService 接口实现
 */
public interface UserAddressService extends IService<UserAddress> {

    List<UserAddress> getList(PageParamRequest pageParamRequest);

    /**
     * 根据基本条件查询
     *
     * @param address 查询条件
     * @return 查询到的地址
     */
    UserAddress getUserAddress(UserAddress address);

    UserAddress create(UserAddressRequest request);

    boolean def(Integer id);

    boolean delete(Integer id);

    UserAddress getDefault();

    UserAddress getById(Integer addressId);

    /**
     * 根据地址参数获取用户收货地址
     */
    List<UserAddress> getListByUserAddress(UserAddress userAddress,
        PageParamRequest pageParamRequest);
}
