package com.fixcomputer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixcomputer.domain.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeService extends IService<Notice> {

    boolean createNotice(Notice notice);

    List<Notice> queryAllNotice();

}
