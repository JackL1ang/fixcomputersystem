package com.fixcomputer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixcomputer.domain.entity.Forum;
import com.fixcomputer.domain.vo.ForumInfo;

import java.util.List;

public interface ForumService extends IService<Forum> {
    boolean saveMessage(Forum forum);

    List<ForumInfo> allMessage();
}
