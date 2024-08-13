package com.fixcomputer.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
@Component
public class JwtTokenUtil {

    private static final Integer ExpTime = 1; //令牌过期天数

    private static final String SECRET = "ljc"; //密钥

    private static final String USERNAME_STRING = "username";

    private static final String STATE_STRING = "state";






    /**
     * 校验Token是否正确并解析Token信息
     */
    public Map validateToken(String token){
        HashMap<String, Object> tokenMap = new HashMap<>();
        if (token == null){
            tokenMap.put(STATE_STRING,false);
            return tokenMap;
        }
        //检验传过来的token是否是正确
        try{
            //校验token
            DecodedJWT verify = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            //获取username
            String username = verify.getClaim(USERNAME_STRING).asString();
            //token过期时间
            Claim exp =verify.getClaim("exp");
            //获取角色

            String roleName = verify.getClaim("role_name").asString();
            if(roleName==null){
                 roleName = verify.getClaim("role").asString();

            }

            tokenMap.put("role",roleName);

            //获取id
            String id = verify.getClaim("id").asString();

            tokenMap.put(STATE_STRING,true);
            tokenMap.put(USERNAME_STRING,username);

            tokenMap.put("id",id);
            return tokenMap;

        }catch (Exception e){
            tokenMap.put(STATE_STRING,false);
            return tokenMap;
        }



    }

    /**
     *
     * 构建令牌
     *
     * */
    public String constructToken(Map<String,Object> map){
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v)->{

            builder.withClaim(k, v.toString());
        });
        Calendar instance = Calendar.getInstance();
        //1天过期
        instance.add(Calendar.DATE,ExpTime);
        //指定令牌过期的时间
        builder.withExpiresAt(instance.getTime());

         return builder.sign(Algorithm.HMAC256(SECRET));
    }

    /**
     *
     * 简单校验
     *
     *
     * */
    public boolean simpleValidateToken(String token){
        try {
            DecodedJWT verify = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            return true;
        }catch (Exception e){
            return false;
        }

    }

}
