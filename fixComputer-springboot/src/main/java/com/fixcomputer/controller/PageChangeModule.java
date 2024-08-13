package com.fixcomputer.controller;


import com.alibaba.fastjson2.JSONObject;
import com.fixcomputer.domain.vo.OrderDetailInfo;
import com.fixcomputer.domain.vo.UserDetailInfo;
import com.fixcomputer.service.PageChangeService;
import com.fixcomputer.utils.JwtTokenUtil;
import com.fixcomputer.utils.RoleNameString;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/page")
public class PageChangeModule {

    @Autowired
    PageChangeService pageChangeService;

    @GetMapping("/{pages}")
    public JSONObject PageOrderChange(@PathVariable int pages, HttpServletRequest request) {
        String token = request.getHeader("token");
        JSONObject jsonObject = new JSONObject();
        if (token != null) {
            JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
            Map map = jwtTokenUtil.validateToken(token);

            String role = (String) map.get("role");
            if (role.equals(RoleNameString.adminRoleName)) {
                List<OrderDetailInfo> orderDetailInfos = pageChangeService.orderPageChangeForAdmin(pages);
                jsonObject.put("order", orderDetailInfos);
                jsonObject.put("status", true);
                return jsonObject;
            } else if (role.equals(RoleNameString.fixerRoleName)) {
                String id = (String) map.get("id");
                List<OrderDetailInfo> orderDetailInfos = pageChangeService.orderPageChangeForFixer(pages, id);
                jsonObject.put("order", orderDetailInfos);
                jsonObject.put("status", true);
                return jsonObject;
            } else if (role.equals(RoleNameString.userRoleName)){
                String id = (String) map.get("id");
                List<UserDetailInfo> userDetailInfos = pageChangeService.orderPageChangeForUser(pages, id);
                jsonObject.put("order", userDetailInfos);
                jsonObject.put("status", true);
                return jsonObject;
            }else{
                jsonObject.put("status",false);
                return jsonObject;
            }


        }else{
            jsonObject.put("status",false);
            return jsonObject;
        }

    }

    @GetMapping("/accept/{pages}")
    public JSONObject acceptOrderChange(@PathVariable int pages){
        JSONObject jsonObject = pageChangeService.fixAcceptOrderPage(pages, 1);
        return jsonObject;

    }





}
