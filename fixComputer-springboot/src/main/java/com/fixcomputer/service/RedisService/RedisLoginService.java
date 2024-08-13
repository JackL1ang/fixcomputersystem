package com.fixcomputer.service.RedisService;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fixcomputer.domain.dto.LoginParams;
import com.fixcomputer.domain.entity.User;
import com.fixcomputer.domain.redisdto.RedisUserInfo;
import com.fixcomputer.domain.vo.AuthInfo;
import com.fixcomputer.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@Service
public class RedisLoginService {

    @Autowired
    RedisTemplate redisTemplate;


    final static String userKey = "User:";

    final static String passwordStr = "password";


    /**
     *
     *使用Redis做登录查找
     *
     * */
    public JSONObject RedisLogin(LoginParams loginParams){
        JSONObject jsonObject = new JSONObject();

        Map entries = redisTemplate.opsForHash().entries(userKey + loginParams.getUsername());
        if(entries.isEmpty()){
            jsonObject.put("state",false);
            return jsonObject;
        }else{
            String password = (String) entries.get("password");
            if(password.equals(loginParams.getPassword())){
                //将数据存入获取token
                Map<String, Object> stringObjectMap = BeanUtil.beanToMap(entries);
                System.out.println(stringObjectMap);
                JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
                String token = jwtTokenUtil.constructToken(stringObjectMap);
                //将数据存入Json 返回
                jsonObject.put("username",stringObjectMap.get("username"));
                jsonObject.put("role",stringObjectMap.get("role_name"));
                jsonObject.put("account_state",stringObjectMap.get("state"));
                jsonObject.put("state",true);
                jsonObject.put("token",token);
                return jsonObject;

            }else {
                jsonObject.put("state",false);
                return jsonObject;
            }
        }



    }

    //not found 将mysql的数据注入到redis中
    public void setRedisInfo(RedisUserInfo redisUserInfo){
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(redisUserInfo);
        redisTemplate.opsForHash().putAll(userKey+redisUserInfo.getUsername().toString(),stringObjectMap);
    }

    /**
     * 遍历所有用户
     *
     * */
    public Map queryAllInfo(String username){
        Map entries = redisTemplate.opsForHash().entries(userKey + username);
        return entries;
    }

    /**
     *
     * 从redis查找密码
     *
     * */
    public boolean queryPassword(String username,String oldPassword){
        String password = redisTemplate.opsForHash().get(userKey + username, "password").toString();
        if(password == null){
            return false;
        }
        if(password.equals(oldPassword)){
            return true;
        }else {
            return false;
        }
    }
    /**
     *
     * Redis更细密码
     *
     * */
    public void updatePassword(String username,String password){
        redisTemplate.opsForHash().put(userKey+username,passwordStr,password);
    }

}
