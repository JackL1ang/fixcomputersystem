package com.fixcomputer.mapper;

import com.fixcomputer.domain.vo.FixAcceptOrder;
import com.fixcomputer.domain.vo.OrderDetailInfo;
import com.fixcomputer.domain.vo.UserDetailInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PageChangeMapper {


    List<OrderDetailInfo> orderPageChangeForAdmin(int page);

    List<OrderDetailInfo> orderPageChangeForFixer(int page,String id);

    List<UserDetailInfo> orderPageChangeForUser(int page, String id);

    List<FixAcceptOrder> acceptOrderPage(int pages, int status);

}
