package com.fixcomputer.ws;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fixcomputer.domain.entity.Role;
import com.fixcomputer.utils.JwtTokenUtil;
import com.fixcomputer.utils.RoleEnum;
import com.fixcomputer.ws.pojo.Message;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ServerEndpoint("/chat")
@Component
public class ChatEndpoint {

    private RedisTemplate redisTemplate;

    private static final Map<String,Session> onlineUsers = new ConcurrentHashMap<>();

    JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    /**
     *
     * 连接建立成功调用的方法
     *
     * */
    @OnOpen
    public void onOpen(Session session){
        //验证传过来的token是否正确
        boolean validateToken = jwtTokenUtil.simpleValidateToken(session.getQueryString());
        if(validateToken){
            //真正进行token解析
            Map map = jwtTokenUtil.validateToken(session.getQueryString());
            log.warn(map.toString());
            String username = map.get("username").toString();
            onlineUsers.put(username, session);
            log.info(onlineUsers.toString());
            log.warn(onlineUsers.toString());
            log.info("和服务器连接");
        }
    }


    /**
     *
     * 收到客户端发送过来的消息
     *
     *
     */
    @OnMessage
    public void onMessage(String msg , Session session){


        log.info("收到消息----->"+msg);
        Message message = JSON.parseObject(msg, Message.class);



        //获取想找到的session
        Session getSession = onlineUsers.get(message.getToName());

        try {
            getSession.getBasicRemote().sendText(msg);
        } catch (Exception e) {
            try {
                session.getBasicRemote().sendText("发送失败,管理员暂时不在线");
            } catch (Exception ex) {
                onMessage(msg,session);
            }
        }

    }



    private void broadcastAllUsers(String message){
        Set<Map.Entry<String ,Session>> entrySet = onlineUsers.entrySet();

    }



    /**
     *
     *链接关闭的方法
     *
     *
     */
    @OnClose
    public void onClose(Session session){
        if(session.getQueryString().isEmpty() || session.getQueryString().equals("undefined")){

        }else {
            System.out.println(session.getQueryString());
            Map map = jwtTokenUtil.validateToken(session.getQueryString());
            String username = (String) map.get("username");

            onlineUsers.remove(username);

            log.info("链接关闭");
        }

    }

}
