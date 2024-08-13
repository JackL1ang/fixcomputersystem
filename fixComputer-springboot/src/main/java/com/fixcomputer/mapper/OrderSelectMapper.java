package com.fixcomputer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixcomputer.domain.entity.CompleteDate;
import com.fixcomputer.domain.entity.Order;
import com.fixcomputer.domain.entity.QRCode;
import com.fixcomputer.domain.vo.FixAcceptOrder;
import com.fixcomputer.domain.vo.OrderDetailInfo;
import com.fixcomputer.domain.vo.OrderStatus;
import com.fixcomputer.domain.vo.UserDetailInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.simpleframework.xml.core.Complete;

import java.util.List;

@Mapper
public interface OrderSelectMapper extends BaseMapper<Order> {
    List<OrderDetailInfo> selectAllOrder();

    OrderDetailInfo selectByOrderId(String id);

    List<FixAcceptOrder> selectFixOrder(int status);

    List<UserDetailInfo> selectOrderByUserId(String id);

    List<OrderDetailInfo> selectOrderByFixerId(String id);


    @Select(
            """
            SELECT o.order_id,
            o.status, 
            s.status_name 
            FROM `t_order` o 
            left join
            `t_status` s ON o.status = s.status_id
            where o.fixer_id = #{user_id} AND
            o.status = 2 or o.status = 3
            

""")
    List<OrderStatus> selectIncompleteOrderById(String user_id);

    @Select(
            """
            SELECT o.order_id, 
            o.status,
            s.status_name 
            FROM `t_order` o 
            left join
            `t_status` s ON o.status = s.status_id
            where o.status = 2 or o.status = 3
""")
    List<OrderStatus> selectIncompleteOrderAll();


    @Select(
            """
        SELECT o.order_id, 
            o.status,
            s.status_name 
            FROM `t_order` o 
            left join
            `t_status` s ON o.status = s.status_id
            where o.user_id = #{user_id}
        """


    )
    List<OrderStatus> selectUserIdForOrderStatus(String user_id);


    CompleteDate selectOrderCompleteDate(String order_id);

    @Select(
            """
            Select count(*) 
            
            FROM `t_order` o
            
            where o.status = #{status}
            
            """
    )
    int countAcceptOrder(int status);


}
