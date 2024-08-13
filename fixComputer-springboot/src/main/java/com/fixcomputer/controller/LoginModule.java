package com.fixcomputer.controller;


import com.alibaba.fastjson2.JSONObject;
import com.fixcomputer.domain.dto.LoginParams;
import com.fixcomputer.domain.entity.User;
import com.fixcomputer.domain.vo.AuthInfo;
import com.fixcomputer.service.RedisService.RedisLoginService;
import com.fixcomputer.service.UserService;
import com.fixcomputer.utils.JwtTokenUtil;
import com.fixcomputer.utils.RoleEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RestController
public class LoginModule {
    @Autowired
    private UserService userService;

    @Autowired
    private ApiInterface apiInterface;


    @PostMapping("login")
    public JSONObject login(HttpServletResponse response
            , @RequestBody LoginParams loginParams){
        response.setCharacterEncoding("utf-8");
        JSONObject jsonInfo = new JSONObject();
        try {
            //将前端发送来的信息传给service去处理
            JSONObject userInfo = userService.login(loginParams);
            //返回给前端
            return userInfo;
        } catch (Exception e) {
            log.warn(e.toString());
            jsonInfo.put("state",false);
            jsonInfo.put("info","服务器错误，请稍后再试");

            //返回给前端
            return jsonInfo;
        }
    }



    /**
     *
     *
     * 注册
     *
     * */
    @PostMapping ("/register")
    public JSONObject register(@RequestBody User userLogin){
        JSONObject registerJson = new JSONObject();
        userLogin.setRole(RoleEnum.User.getCode());

        if (userLogin.getUsername() == "" || userLogin.getPassword() == ""){
            registerJson.put("state",false);
            registerJson.put("message","用户名或密码不能为空");
            return registerJson;
        }
        JSONObject register = userService.register(userLogin);
        return register;
    }



    @PostMapping("/test")
    public JSONObject testPost(HttpServletRequest request) throws IOException {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("213",123);
        jsonObject1.put("123",222);
        System.out.println(request.getHeader("token"));
        JSONObject jsonObject = apiInterface.tokenToUsername(request);
        System.out.println(jsonObject);
        return jsonObject1;

    }





}
