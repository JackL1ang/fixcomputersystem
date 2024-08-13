package com.fixcomputer.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixcomputer.domain.entity.UserAddress;
import com.fixcomputer.mapper.UserAddressInfoMapper;
import com.fixcomputer.service.UserAddressInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressInfoServiceImpl extends ServiceImpl<UserAddressInfoMapper, UserAddress> implements UserAddressInfoService {

    @Autowired
    UserAddressInfoMapper userAddressInfoMapper;

    //新建地址
    @Override
    public boolean createUserAddress(UserAddress userAddress) {
        int insert = userAddressInfoMapper.insert(userAddress);
        if(insert == 1){
            return  true;
        }else {
            return false;
        }
    }


    //查询地址从id
    @Override
    public List<UserAddress> selectAllAddressForId(String user_id) {
        QueryWrapper<UserAddress> userAddressQueryWrapper = new QueryWrapper<>();
        userAddressQueryWrapper.eq("user_id",user_id);
        List<UserAddress> userAddresses = userAddressInfoMapper.selectList(userAddressQueryWrapper);

        return userAddresses;
    }

    /**
     *
     * 更改地址
     *
     * */
    @Override
    public boolean modifyAddress(UserAddress userAddress) {
        UpdateWrapper<UserAddress> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",userAddress.getId());
        updateWrapper.eq("user_id",userAddress.getUserId());
        int update = userAddressInfoMapper.update(userAddress, updateWrapper);
        if(update==1){
            return true;
        }else {
            return false;
        }

    }

    /**
     *
     * 修改默认地址
     *
     * */
    @Override
    public boolean updateDefaultAddress(String user_id, String new_id, String old_id) {
        //把旧默认地址 修改为非默认地址
        QueryWrapper<UserAddress> oldQueryWrapper = new QueryWrapper<>();
        oldQueryWrapper.eq("id",old_id);
        oldQueryWrapper.eq("user_id",user_id);
        UserAddress userAddress = new UserAddress();
        userAddress.setDefaultAddress(false);
        int update = userAddressInfoMapper.update(userAddress, oldQueryWrapper);
        //把新的地址设为默认地址
        if(update==1){
            QueryWrapper<UserAddress> newQueryWrapper = new QueryWrapper<>();
            newQueryWrapper.eq("id",new_id);
            newQueryWrapper.eq("user_id",user_id);
            UserAddress userAddress1 = new UserAddress();
            userAddress1.setDefaultAddress(true);
            int update1 = userAddressInfoMapper.update(userAddress1, newQueryWrapper);
            if(update1==1){
                return true;
            }else {
                return false;
            }

        }else {
            return false;
        }



    }

    @Override
    public boolean firstSetDefaultAddress(String user_id, String addressId) {
        QueryWrapper<UserAddress> userAddressQueryWrapper = new QueryWrapper<>();
        userAddressQueryWrapper.eq("user_id",user_id);
        userAddressQueryWrapper.eq("id",addressId);
        UserAddress userAddress = new UserAddress();
        userAddress.setDefaultAddress(true);
        int update = userAddressInfoMapper.update(userAddress, userAddressQueryWrapper);
        if(update>=1){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public UserAddress queryDefaultAddress(String user_id) {

        QueryWrapper<UserAddress> addressQueryWrapper = new QueryWrapper<>();

        addressQueryWrapper.eq("user_id",user_id);

        addressQueryWrapper.eq("default_address",1);

        UserAddress userAddress = userAddressInfoMapper.selectOne(addressQueryWrapper);


        return userAddress;
    }




}
