package com.fixcomputer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixcomputer.domain.entity.Parts;
import jakarta.servlet.http.Part;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PartsMapper extends BaseMapper<Parts> {

    @Update("update t_parts set inventory = inventory - #{amount} where parts_id = #{parts_id}")
    int subStock(String parts_id, int amount);


    @Update("""
        update t_parts 
        set 
            inventory = inventory + #{amount}
        where 
            parts_id = #{parts_id}


        """)
    int updatePartsStock(int amount,String parts_id);





}
