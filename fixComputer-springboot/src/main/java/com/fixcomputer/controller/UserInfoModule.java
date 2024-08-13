package com.fixcomputer.controller;


import com.fixcomputer.domain.dto.ModifyPassword;
import com.fixcomputer.domain.entity.User;
import com.fixcomputer.domain.vo.UserInfo;
import com.fixcomputer.service.UserService;
import com.fixcomputer.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserInfoModule {
    @Autowired
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    /**
     *
     * 获取所有用户信息
     *
     * */
    @GetMapping("/all")
    public List<User> getAllUserInfo(){
        List<User> users = userService.selectUserByAll();
        return users;
    }
    /**
     *
     * 获取用户信息获取userid
     *
     * */
    @GetMapping
    public UserInfo getUserForId(String userId){

        UserInfo user = userService.selectUserById(userId);

        return user;

    }
    /**
     *
     * 修改密码
     *
     * */
    @PostMapping("modifypw")
    public boolean modifyPassword(HttpServletRequest request , @RequestBody ModifyPassword modifyPassword){
        if(modifyPassword.getPassword().equals(modifyPassword.getCheckPass())){
            String token = request.getHeader("token");
            Map tokenMap = jwtTokenUtil.validateToken(token);
            String user_id = (String) tokenMap.get("id");
            modifyPassword.setUser_id(user_id);
            String username = (String) tokenMap.get("username");
            modifyPassword.setUser_name(username);
            boolean b = userService.modifyPassword(modifyPassword);
            return b;

        }else {
            return false;
        }

    }

    /**
     *
     * 修改用户信息
     *
     * */
    @PutMapping
    public boolean modifyUserInfo(@RequestBody UserInfo userInfo){
        boolean modifyUserInfo = userService.modifyUserInfo(userInfo);
        return modifyUserInfo ;
    }
}


