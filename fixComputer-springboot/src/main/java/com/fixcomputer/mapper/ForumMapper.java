package com.fixcomputer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixcomputer.domain.entity.Forum;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ForumMapper extends BaseMapper<Forum> {
}
