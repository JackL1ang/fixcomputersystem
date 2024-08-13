package com.fixcomputer.controller;


import com.alibaba.fastjson2.JSONObject;
import com.fixcomputer.mapper.StatisticalDataMapper;
import com.fixcomputer.service.StatisticalDataService;
import com.fixcomputer.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("statistic")
public class StatisticDateModule {

    @Autowired
    StatisticalDataService statisticalDataService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public JSONObject StatisticOrder(HttpServletRequest request){
        String token = request.getHeader("token");
        if(token!=null){
            Map map = jwtTokenUtil.validateToken(token);

            String role = (String) map.get("role");

            String user_id = (String) map.get("id");


            if(role.equals("admin")){
                JSONObject jsonObject = statisticalDataService.CountstatisticAll();
                return jsonObject;
            }else if(role.equals("fixer")){
                JSONObject jsonObject = statisticalDataService.CountstatisticById(user_id);
                return jsonObject;
            }else{
                JSONObject jsonObject = statisticalDataService.CountUserOrder(user_id);
                return jsonObject;
            }
        }else {
            return null;
        }


    }
}
