package com.fixcomputer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixcomputer.domain.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    @Select(

        """
        SELECT 
        n.id,
        n.title,
        n.content,
        n.create_time,
        u.username AS author
        FROM `t_notice` n 
        left join `t_user` u on n.author = u.id
        
        """

    )
    List<Notice> queryAllNotice();


}
