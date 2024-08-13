package com.fixcomputer.service.serviceImpl;



import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixcomputer.domain.dto.LoginParams;
import com.fixcomputer.domain.dto.ModifyPassword;
import com.fixcomputer.domain.entity.User;
import com.fixcomputer.domain.entity.UserAddress;
import com.fixcomputer.domain.redisdto.RedisUserInfo;
import com.fixcomputer.domain.vo.AuthInfo;
import com.fixcomputer.domain.vo.UserInfo;
import com.fixcomputer.mapper.UserAddressInfoMapper;
import com.fixcomputer.mapper.UserMapper;
import com.fixcomputer.service.RedisService.RedisLoginService;
import com.fixcomputer.service.UserService;
import com.fixcomputer.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    RedisLoginService redisLoginService;

    @Autowired
    UserAddressInfoMapper userAddressInfoMapper;


    /**
     *
     * 登录模块
     *
     * */
    public JSONObject login(LoginParams loginParams) throws Exception {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        JSONObject jsonInfo = new JSONObject();
        HashMap<String, Object> userMap = new HashMap<>();
        JSONObject jsonObject = redisLoginService.RedisLogin(loginParams);
        if((boolean) jsonObject.get("state")){
            return jsonObject;
        }else {
            AuthInfo userInfo = userMapper.login(loginParams.getUsername(), loginParams.getPassword());
            if (userInfo != null) {
                //将查询到的信息放入map
                userMap.put("username", userInfo.getUser().getUsername());
                userMap.put("id", String.valueOf(userInfo.getUser().getId()));
                userMap.put("role", userInfo.getRole().getRole_name());
                //使用Jwt工具类生成token
                String token = jwtTokenUtil.constructToken(userMap);

                jsonInfo.put("username",userInfo.getUser().getUsername());
                jsonInfo.put("role",userInfo.getRole().getRole_name());
                jsonInfo.put("state", true);
                jsonInfo.put("token", token);

                //将查询到的userinfo存入redis
                RedisUserInfo redisUserInfo = new RedisUserInfo();
                redisUserInfo.setId(userInfo.getUser().getId());
                redisUserInfo.setUsername(userInfo.getUser().getUsername());
                redisUserInfo.setPassword(userInfo.getUser().getPassword());
                redisUserInfo.setRole_name(userInfo.getRole().getRole_name());
                redisUserInfo.setState(userInfo.getUser().getState());
                redisLoginService.setRedisInfo(redisUserInfo);
                return jsonInfo;
            } else {
                jsonInfo.put("state", false);
                jsonInfo.put("info", "用户名或密码错误，请重试");
                return jsonInfo;
            }
        }
    }



    /**
     *
     * 注册
     *
     * */
    @Transactional
    public JSONObject register(User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",user.getUsername());
        JSONObject jsonObject = new JSONObject();
        //判断用户名是否存在
        boolean exists = userMapper.exists(userQueryWrapper);
        if(exists){
            jsonObject.put("state",false);
            jsonObject.put("message","用户名已存在,请重新换一个");
            return jsonObject;
        }else{
            try{
                //将账号设置为可用
                user.setState((byte) 1);
                userMapper.insert(user);
                jsonObject.put("state",true);
                jsonObject.put("message","注册成功，即将跳转");
                return jsonObject;
            }catch (Exception e){
                log.warn(e.toString());
                jsonObject.put("state",false);
                jsonObject.put("message","服务器异常，请稍后再试");
                return jsonObject;
            }


        }
    }

    /**
     *查询所有用户
     *
     * */
    public List<User> selectUserByAll() {

        List<User> users = userMapper.selectList(new QueryWrapper<User>());

        return users;

    }


    //使用用户id查询用户信息
    @Override
    public UserInfo selectUserById(String id) {
        UserInfo userInfo = userMapper.selectUserInfoById(id);
        return userInfo;
    }

    /**
     *
     * 修改密码
     *
     * */
    @Override
    public boolean modifyPassword(ModifyPassword modifyPassword) {
        boolean redisQueryPassword = redisLoginService.queryPassword(modifyPassword.getUser_name(), modifyPassword.getOldPassword());

        //如果再redis能查询到 直接写入mysql
        if(redisQueryPassword){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",modifyPassword.getUser_id());
            User user = new User();
            user.setPassword(modifyPassword.getPassword());
            int update = userMapper.update(user, queryWrapper);

            if(update ==1){
                //更新redis中的密码
                redisLoginService.updatePassword(modifyPassword.getUser_name(), modifyPassword.getPassword());
                return true;
            }else {
                return false;
            }
        }else{
            //redis如查询不到 查询mysql是否存在用户信息
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",modifyPassword.getUser_id());
            queryWrapper.eq("password",modifyPassword.getOldPassword());
            boolean exists = userMapper.exists(queryWrapper);
            //存在修改
            if(exists){
                QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
                objectQueryWrapper.eq("id",modifyPassword.getUser_id());
                User user = new User();
                user.setPassword(modifyPassword.getPassword());
                int update = userMapper.update(user, objectQueryWrapper);
                if(update == 1){
                    //写入redis
                    redisLoginService.updatePassword(user.getUsername(), user.getPassword());
                    return true;
                }else{
                    return false;
                }


            }else{
                return false;
            }
        }

    }

    /**
     *
     * 修改用户信息
     *
     * */
    @Override
    public boolean modifyUserInfo(UserInfo userInfo) {
        //先修改用户信息
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id",userInfo.getId());
        User user = new User();
        BeanUtil.copyProperties(userInfo,user);
        int update = userMapper.update(user, userUpdateWrapper);
        //将修改后用户信息写入redis

        AuthInfo authInfo = userMapper.userInfoById(userInfo.getId());
        RedisUserInfo redisUserInfo = new RedisUserInfo();
        redisUserInfo.setId(authInfo.getUser().getId());
        redisUserInfo.setUsername(authInfo.getUser().getUsername());
        redisUserInfo.setPassword(authInfo.getUser().getPassword());
        redisUserInfo.setRole_name(authInfo.getRole().getRole_name());
        redisUserInfo.setState(authInfo.getUser().getState());
        redisLoginService.setRedisInfo(redisUserInfo);




        if(update == 1){
            //修改地址
            UpdateWrapper<UserAddress> userAddressUpdateWrapper = new UpdateWrapper<>();
            userAddressUpdateWrapper.eq("id",userInfo.getInfoId());
            userAddressUpdateWrapper.eq("user_id",userInfo.getId());
            //查询用户有无默认地址 如无就不修改
            QueryWrapper<UserAddress> userAddressQueryWrapper = new QueryWrapper<>();
            userAddressQueryWrapper.eq("user_id",userInfo.getId());
            boolean exists = userAddressInfoMapper.exists(userAddressQueryWrapper);
            if(exists){
                UserAddress userAddress = new UserAddress();
                BeanUtil.copyProperties(userInfo,userAddress);
                userAddress.setId(null);
                userAddress.setUserId(null);
                userAddress.setDefaultAddress(true);
                int update1 = userAddressInfoMapper.update(userAddress, userAddressUpdateWrapper);

                if(update1 == 1){
                    return true;
                }else{
                    return false;
                }
            }else{
                return true;
            }

        }else{
            return false;
        }


    }

}



