package com.fixcomputer.controller;


import com.fixcomputer.domain.entity.Notice;
import com.fixcomputer.service.NoticeService;
import com.fixcomputer.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notice")
public class NoticeModule {
    @Autowired
    NoticeService noticeService;

    @PostMapping
    public boolean createNotice(@RequestBody Notice notice,HttpServletRequest request){
        String token = request.getHeader("token");
        JwtTokenUtil jwtTokenUtil1 = new JwtTokenUtil();
        Map map = jwtTokenUtil1.validateToken(token);
        String id = (String) map.get("id");
        notice.setAuthor(id);
        boolean noticeServiceNotice = noticeService.createNotice(notice);

        return noticeServiceNotice;
    }

    @GetMapping
    public List<Notice> queryAllNotice(){
        List<Notice> notices = noticeService.queryAllNotice();
        return notices;
    }




}
