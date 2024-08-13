package com.fixcomputer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixcomputer.domain.entity.User;
import com.fixcomputer.domain.entity.UserAddress;

import java.util.List;

public interface UserAddressInfoService extends IService<UserAddress> {

    boolean createUserAddress(UserAddress userAddress);

    List<UserAddress> selectAllAddressForId(String user_id);

    boolean modifyAddress(UserAddress userAddress);

    boolean updateDefaultAddress(String user_id,String new_id,String old_id);

    boolean firstSetDefaultAddress(String user_id, String addressId);



    UserAddress queryDefaultAddress(String user_id);



}
