package com.fixcomputer.controller;


import com.fixcomputer.domain.entity.UserAddress;
import com.fixcomputer.service.UserAddressInfoService;
import com.fixcomputer.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user_address")
public class UserAddressInfoModule {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserAddressInfoService userAddressInfoServiceImpl;

    /**
     *
     * 添加用户地址
     *
     * */
    @PostMapping
    public boolean createUserAddress(@RequestBody UserAddress userAddress,HttpServletRequest request){
        System.out.println(userAddress);
        String token = request.getHeader("token");
        Map map = jwtTokenUtil.validateToken(token);
        String id = (String) map.get("id");
        userAddress.setUserId(id);
        System.out.println(id);
        boolean userAddressStatus = userAddressInfoServiceImpl.createUserAddress(userAddress);
        return userAddressStatus;
    }

    /**
     *
     * 查询所有地址
     *
     * */
    @GetMapping
    public List<UserAddress> selectAllAddressForId(HttpServletRequest request){
        String token = request.getHeader("token");
        System.out.println(token);
        Map map = jwtTokenUtil.validateToken(token);
        String id = (String) map.get("id");

        List<UserAddress> userAddress = userAddressInfoServiceImpl.selectAllAddressForId(id);
        return userAddress;
    }

    /**
     *
     * 修改用户地址
     *
     * */

    @PutMapping
    public boolean updateAddress(@RequestBody UserAddress userAddress){
        boolean modifyAddress = userAddressInfoServiceImpl.modifyAddress(userAddress);
        return modifyAddress;
    }

    @GetMapping("/{id}/")
    public boolean firstSetDefaultAddress(@PathVariable String id,HttpServletRequest request){
        String token = request.getHeader("token");
        JwtTokenUtil jwtTokenUtil1 = new JwtTokenUtil();
        Map map = jwtTokenUtil1.validateToken(token);
        String user_id = (String) map.get("id");
        boolean firstSetDefaultAddress = userAddressInfoServiceImpl.firstSetDefaultAddress(user_id, id);
        return firstSetDefaultAddress;
    }



    /**
     *
     * 更改默认地址
     *
     * */
    @GetMapping("/{id}/{old_id}")
    public boolean updateDefaultAddress(@PathVariable String id ,@PathVariable String old_id ,HttpServletRequest request){
        String token = request.getHeader("token");
        if(token !=null){
            Map map = jwtTokenUtil.validateToken(token);
            String user_id = (String) map.get("id");

            boolean updateDefaultAddress = userAddressInfoServiceImpl.updateDefaultAddress(user_id, id, old_id);

            return updateDefaultAddress;
        }else{
            return false;
        }
    }

    /**
     *
     * 查询默认地址
     *
     */
    @GetMapping("query_defaultAddress")
    public UserAddress queryDefaultAddress(HttpServletRequest request){
        String token = request.getHeader("token");

        if(token != null ){

            JwtTokenUtil jwtTokenUtil1 = new JwtTokenUtil();
            Map map = jwtTokenUtil1.validateToken(token);
            String user_id = (String) map.get("id");
            UserAddress userAddress = userAddressInfoServiceImpl.queryDefaultAddress(user_id);

            return userAddress;
        }else {
            return null;
        }

    }

}
