package com.fixcomputer.controller;

import com.fixcomputer.domain.entity.Forum;
import com.fixcomputer.domain.vo.Comment;
import com.fixcomputer.domain.vo.ForumInfo;
import com.fixcomputer.service.ForumService;
import com.fixcomputer.service.RedisService.RedisCommentService;
import com.fixcomputer.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/forum")
public class ForumModule {

    @Autowired
    ForumService forumService;

    @Autowired
    RedisCommentService redisCommentService;

    /**
     *
     *保存贴子
     *
     * */
    @PostMapping
    public boolean saveSendMessage(@RequestBody Forum forum, HttpServletRequest request){
        String token = request.getHeader("token");
        if(!token.isEmpty()&&!token.isEmpty()){
            JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
            Map map = jwtTokenUtil.validateToken(token);
            String username = (String) map.get("username");
            forum.setAuthor(username);
            boolean b = forumService.saveMessage(forum);
            if(b){
                return b;
            }else{
                return b;
            }
        }else {
            return false;
        }

    }



    @GetMapping
    public List<ForumInfo> getAllMessage(){
        List<ForumInfo> forums = forumService.allMessage();

        return forums;
    }

    @PostMapping("/comment")
    public Comment saveComment(@RequestBody Comment comment,HttpServletRequest request){

        String token = request.getHeader("token");
        if(!token.isEmpty()&&!token.isBlank()){
            JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
            Map map = jwtTokenUtil.validateToken(token);
            String username = (String) map.get("username");
            comment.setAuthor(username);
            Comment redisSaveComment = redisCommentService.RedisSaveComment(comment);
            if (redisSaveComment != null){
                return redisSaveComment;
            }else {
                return null;
            }

        }else {
            return null;
        }
    }

    @GetMapping("/comment/{postId}")
    public Set<Comment> getComment(@PathVariable String postId){
        Set<Comment> commentsByPostId = redisCommentService.getCommentsByPostId(postId);

        return commentsByPostId;
    }
}
