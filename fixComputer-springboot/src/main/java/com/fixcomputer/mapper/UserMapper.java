package com.fixcomputer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixcomputer.domain.entity.User;
import com.fixcomputer.domain.vo.AuthInfo;
import com.fixcomputer.domain.vo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    AuthInfo login (String username, String password);



    @Select(
            """
        
        SELECT u.id,u.username,u.`password`,u.role,u.state,i.id as infoId ,i.address,i.phone,i.`name`,i.province,i.city,i.area
        FROM `t_user` u
        left join `t_userinfo` i on i.user_id = u.id AND i.default_address !=0
        WHERE u.id = #{user_id} AND
        (i.id IS NULL OR i.default_address = 1)
        
        
        """)
    UserInfo selectUserInfoById(String user_id);


    AuthInfo userInfoById(String id);

}
