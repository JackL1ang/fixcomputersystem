package com.fixcomputer.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fixcomputer.domain.dto.LoginParams;
import com.fixcomputer.domain.dto.ModifyPassword;
import com.fixcomputer.domain.entity.User;
import com.fixcomputer.domain.vo.UserInfo;

import java.util.List;


public interface UserService extends IService<User> {

    JSONObject register(User user);


    JSONObject login(LoginParams loginParams) throws Exception;

    List<User> selectUserByAll();

    UserInfo selectUserById(String id);

    boolean modifyPassword(ModifyPassword modifyPassword);

    boolean modifyUserInfo(UserInfo userInfo);
}
