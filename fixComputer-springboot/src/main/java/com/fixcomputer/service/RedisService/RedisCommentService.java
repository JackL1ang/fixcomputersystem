package com.fixcomputer.service.RedisService;


import com.fixcomputer.domain.vo.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RedisCommentService {
    @Autowired
    RedisTemplate redisTemplate;

    private static final String COMMENTS_KEY_PREFIX="comments:";



    /**
     *
     * 存储评论
     *
     * */

    public Comment RedisSaveComment(Comment comment){
        Date date = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String format = simpleDateFormat.format(date);

        comment.setCreateTime(format);

        comment.setCommentId(UUID.randomUUID().toString());

        try {
            String key = COMMENTS_KEY_PREFIX+comment.getSendId();

            HashOperations<String,Object,Comment> listOperations = redisTemplate.opsForHash();

            listOperations.put(key,comment.getCommentId(),comment);

            return comment;
        }catch (Exception e){

            log.warn(e.toString());
            return null;

        }
    }



    /**
     *
     * 查询贴子下所有的评论
     *
     *
     */
    public Set<Comment> getCommentsByPostId(String postId){
        String key = COMMENTS_KEY_PREFIX+postId;
        HashOperations<String,Object,Comment> hashOperations = redisTemplate.opsForHash();
        /**
         *
         * 使用entries方法获取哈希表中所有的字段和值。由于entries方法返回的是一个Map<Object, Object>，
         * 我们需要将其转换为一个Set<Comment>。这可以通过将Map的values流映射到Comment集合，
         * 然后收集到一个Set中来实现。
         *
         * */
        return hashOperations.entries(key).entrySet().stream()
                .map(entry -> entry.getValue()).collect(Collectors.toSet());

    }

    /**
     *
     * 查询评论的数量
     *
     * */
    public Long getCommentCountById(String postId){
        String key = COMMENTS_KEY_PREFIX+postId;
        Long size = redisTemplate.opsForHash().size(key);
        return size;

    }

}
