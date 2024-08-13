package com.fixcomputer.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixcomputer.domain.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface StatisticalDataMapper extends BaseMapper<Order> {

    @Select("SELECT category as name ,Count(*) as value FROM `t_order`GROUP BY category")
    List<Map<String,Object>> selectCountCategoryAll();
    @Select("SELECT category as name ,Count(*) as value FROM `t_order` where fixer_id = #{id} GROUP BY category")
    List<Map<String,Object>> selectCountCategoryById(String id);

    @Select("SELECT COUNT(*) FROM `t_order`")
    double countOrderAll();

    @Select("SELECT COUNT(*) FROM `t_order` where fixer_id = #{id}")
    double countOrderById(String id);

    @Select("SELECT COUNT(*) FROM `t_order` where status=0")
    double countCompleteOrderAll();

    @Select("SELECT COUNT(*) FROM `t_order` where fixer_id = #{id} and status=0")
    double countCompleteOrderById(String id);

    @Select("SELECT COUNT(*) FROM `t_order` where status=2 or status=3")
    double countInCompleteOrderAll();
    @Select("SELECT COUNT(*) FROM `t_order` where fixer_id = #{id} and (status=2 or status=3)")
    double countInCompleteOrderById(String id);

    @Select("SELECT COUNT(*) FROM `t_order` where user_id = #{id} and status=0")
    double countCompleteOrderByUserId(String id);

    @Select("SELECT COUNT(*) FROM `t_order` where user_id = #{id} and (status=2 or status=3 or status = 1)")
    double countInCompleteOrderByUserId(String id);

    @Select("SELECT COUNT(*) FROM `t_order` where user_id = #{id}")
    double countOrderByUserId(String id);





    @Select("""
           SELECT 
               DATE (create_date) as orderDate,
               Count(*) as orderCount
           FROM
               t_order
           WHERE
               fixer_id = #{user_id}
               AND DATE(create_date) >= DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) + 1 DAY)
               AND DATE(create_date) <= CURDATE()
           GROUP BY
               DATE(create_date)
           ORDER BY
               DATE(create_date)
               
           
           """)
    List<Map<String,Object>> countWeekOrderById(String user_id);



    @Select("""
           SELECT 
               DATE (create_date) as orderDate,
               Count(*) as orderCount
           FROM
               t_order
           WHERE
               DATE(create_date) >= DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) + 1 DAY)
               AND DATE(create_date) <= CURDATE()
           GROUP BY
               DATE(create_date)
           ORDER BY
               DATE(create_date)
               
           
           """)
    List<Map<String,Object>> countWeekOrderAll();



    @Select("""
        SELECT 
            DATE_FORMAT(create_date,'%Y-%m') as orderMonth,
            COUNT(*) as orderCount
        FROM
            t_order
        WHERE
            fixer_id = #{fixer_id} AND
            DATE_FORMAT(create_date,'%Y') = #{year}
        GROUP BY
            orderMonth
        


    """)
    List<Map<String,Object>> CountYearOrderById(String fixer_id,int year);




    @Select("""
        SELECT 
            DATE_FORMAT(create_date,'%Y-%m') as orderMonth,
            COUNT(*) as orderCount
        FROM
            t_order
        WHERE
            DATE_FORMAT(create_date,'%Y') = #{year}
        GROUP BY
            orderMonth
    """)
    List<Map<String,Object>> CountYearOrderAll(int year);


}
