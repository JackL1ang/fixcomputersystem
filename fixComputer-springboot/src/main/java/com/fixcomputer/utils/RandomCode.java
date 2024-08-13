package com.fixcomputer.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Base64Util;

import java.net.http.HttpRequest;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

public class RandomCode {
    public static final String KEY_ALGORITHM = "RSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";

    private static final String PRIVATE_KEY = "RSAPrivateKey";

    private static final String PRIVATE_CODE = "PrivateKey";
    //通过传入request生成随机code
    public String generateKey(HttpServletRequest request){
        try {
            Map<String, Object> keyMap = genKeyPair();
            String publicKey = getPublicKey(keyMap);
            String privateKey = getPrivateKey(keyMap);
            request.getSession().setAttribute(PRIVATE_CODE,privateKey);
            return publicKey;

        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }
    public static Map<String,Object> genKeyPair() throws Exception {
        //指定的算法生成公钥/私钥对
        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance(KEY_ALGORITHM);

        //initialize方法设置密钥大小
        keyPairGenerator.initialize(1024);

        //调用generateKeyPair方法生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPublicKey PublicKey = (RSAPublicKey) keyPair.getPublic();

        RSAPrivateKey PrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        HashMap<String, Object> keyMap = new HashMap<>();

        keyMap.put(PUBLIC_KEY,PublicKey);
        keyMap.put(PRIVATE_KEY,PrivateKey);

        return keyMap;

    }

    /**
     *
     *获取私钥
     *
     * */
    public static String getPrivateKey(Map<String,Object> keyMap){
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Util.encode(String.valueOf(key.getEncoded()));
    }

    /**
     *
     * 获取公钥
     *
     * */
    public static String getPublicKey(Map<String,Object> keyMap){
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Util.encode(String.valueOf(key.getEncoded()));
    }
}
