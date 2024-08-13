package com.fixcomputer.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixcomputer.domain.entity.Notice;
import com.fixcomputer.mapper.NoticeMapper;
import com.fixcomputer.service.NoticeService;
import com.fixcomputer.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper,Notice> implements NoticeService {

    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public boolean createNotice(Notice notice) {
        DateUtil dateUtil = new DateUtil();
        String month = dateUtil.dateUtilMonth();
        notice.setCreateTime(month);
        int insert = noticeMapper.insert(notice);
        if(insert == 1){
            return true;
        }else{
            return false;
        }


    }

    @Override
    public List<Notice> queryAllNotice() {
        List<Notice> notices = noticeMapper.queryAllNotice();
        return notices;
    }
}
