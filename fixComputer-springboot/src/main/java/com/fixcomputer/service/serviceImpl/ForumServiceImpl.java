package com.fixcomputer.service.serviceImpl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixcomputer.domain.entity.Forum;
import com.fixcomputer.domain.vo.Comment;
import com.fixcomputer.domain.vo.ForumInfo;
import com.fixcomputer.mapper.ForumMapper;
import com.fixcomputer.service.ForumService;
import com.fixcomputer.service.RedisService.RedisCommentService;
import com.fixcomputer.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ForumServiceImpl extends ServiceImpl<ForumMapper, Forum> implements ForumService {

    @Autowired
    ForumMapper forumMapper;

    @Autowired
    RedisCommentService redisCommentService;





    public boolean saveMessage(Forum forum){

        DateUtil dateUtil = new DateUtil();
        Date date = dateUtil.dateUtilForDate();

        forum.setCreateTime(date);
        forum.setSendId(UUID.randomUUID().toString());

        int insert = forumMapper.insert(forum);
        if(insert==1){
            return true;

        }else {
            return false;
        }

    }


    public List<ForumInfo> allMessage(){

        Page<Forum> page = new Page<>(1, 9);

        Page<Forum> page1 = forumMapper.selectPage(page, null);

        System.out.println("-----------__>"+page1.getRecords());


        List<Forum> forums = forumMapper.selectList(new QueryWrapper<>());

        ArrayList<ForumInfo> arrayList = new ArrayList<>();
        for (Forum forum : forums) {
            ForumInfo forumInfo = BeanUtil.copyProperties(forum, ForumInfo.class);
            Long commentCountById = redisCommentService.getCommentCountById(forum.getSendId());
            forumInfo.setComment_size(commentCountById);
            arrayList.add(forumInfo);
        }
        return arrayList;
    }




}
