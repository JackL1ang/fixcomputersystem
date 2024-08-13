package com.fixcomputer;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixcomputer.config.MinioConfig;
import com.fixcomputer.domain.entity.*;
import com.fixcomputer.domain.vo.*;
import com.fixcomputer.mapper.*;
import com.fixcomputer.service.*;
import com.fixcomputer.service.RedisService.RedisCommentService;
import com.fixcomputer.service.RedisService.RedisLoginService;
import com.fixcomputer.service.serviceImpl.QRCodeServiceImpl;
import com.fixcomputer.service.serviceImpl.StatisticalDataServiceImpl;
import com.fixcomputer.utils.DateUtil;
import com.fixcomputer.utils.JwtTokenUtil;
import com.fixcomputer.utils.RandomCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;


import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Unit test for simple App.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class AppTest{

    @Autowired
    UserMapper userMapper;

    @Autowired
    OrderSelectService orderSelectService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    UserService userService;

    @Autowired
    PartsService partsService;

    @Autowired
    UserAddressInfoService userAddressInfoService;

    @Autowired
    StatisticalDataServiceImpl statisticalDataService;

    @Autowired
    OrderUpdateService orderUpdateService;

    @Autowired
    RepairInfoMapper repairInfoMapper;

    @Autowired
    StatisticalDataMapper statisticalDataMapper;

    @Autowired
    RedisLoginService redisLoginService;

    @Autowired
    OrderSelectMapper orderSelectMapper;

    @Autowired
    PageChangeService pageChangeService;

    @Autowired
    PartsMapper partsMapper;

    @Autowired
    UserAddressInfoMapper userAddressInfoMapper;

    @Autowired
    UsePartsMapper usePartsMapper;

    @Autowired
    QRCodeServiceImpl qrCodeServiceImpl;

    @Autowired
    MinioConfig minioConfig;

    @Autowired
    RedisCommentService redisCommentService;

    @Autowired
    MapCoordianteMapper mapCoordinate;

    @Autowired
    PayInfoMapper payInfoMapper;


    @Test
    public void test(){
        String token = "eyJhbGciOiJIUzI11iIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEiLCJ1c2VybmFtZSI6ImFkbWluIiwiZXhwIjoxNzAzNzc0OTUzfQ.keA2t-gJTMb1gJNdQzcB6GAuJN_r7O5Nxer8XjBXPl4";
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
//        Map<String, String> map = new HashMap<>();
//        map.put("username","123");
//        map.put("password","123");
//        String token = jwtTokenUtil.constructToken(map);
//        System.out.println(token);

        Object o =  jwtTokenUtil.validateToken(token);
        System.out.println(o);

    }

    @Test

    public void test1(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username","admin");
        queryWrapper.eq("password","0");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void test2(){
//        RandomCode randomCode = new RandomCode();
        try {
            Map<String, Object> stringObjectMap = RandomCode.genKeyPair();
            Key rsaPublicKey = (Key) stringObjectMap.get("RSAPublicKey");
            String encode = Base64Util.encode(String.valueOf(rsaPublicKey.getEncoded()));
            System.out.println(encode);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test3(){
        int[] nums = {1,7,3,6,5,6};

        int half_Tag = nums.length/2;
        int left_count  = 0 ;
        int right_count  = 0;
        for(int i =0 ; i<half_Tag ; i++){
            left_count+=nums[i];
        }
        for(int i = nums.length;i<half_Tag;i--){
            right_count+=nums[i];
        }
        System.out.println(left_count);
        System.out.println(right_count);
        if(right_count == left_count){
            System.out.println(half_Tag);
        }else{
            System.out.println(-1);
        }
    }

    @Transactional
    @Test
    public void test4(){
//         AuthInfo login = userMapper.login("admin", "admin");
//        System.out.println(login);
        Calendar instance = Calendar.getInstance();
        Date time = instance.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String format1 = format.format(time);
        System.out.println(format1);

        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
        String format2 = f.format(time);
        Random random = new Random();
        int i = random.nextInt(10000);
        long a = Long.parseLong(format2+i);
        System.out.println(a);


    }
    @Test
    public void test5(){
        Page<Parts> parts = partsService.selectAllPartsByPage(1);
    }

    @Test
    public void test6(){
        List<UserAddress> userAddress = userAddressInfoService.selectAllAddressForId("1");
        for (UserAddress address : userAddress) {
            System.out.println(address);
        }

    }

    @Test
    public void test7(){
        statisticalDataService.selectCategoryCountById("3");
    }

    @Test
    public void test8(){

//
        List<User> users = userService.selectUserByAll();


            users.forEach(user1 -> {

                    Map<String, Object> stringObjectMap = BeanUtil.beanToMap(user1);

                    redisTemplate.opsForHash().putAll("User:"+user1.getId().toString(),stringObjectMap);

            });



    }


    @Test
    public void test9(){


        Map<Object, Object> entries = redisTemplate.opsForHash().entries("User:1");
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(entries);
        System.out.println(stringObjectMap);
//        if (entries.isEmpty()){
//            System.out.println(123);
//        }else {
//            System.out.println(456);
//        }

//        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(entries);
//        System.out.println(stringObjectMap);

//        System.out.println(s);

//        if (o != null){
//            System.out.println(123);
//        }else {
//            System.out.println(o);
//        }

    }

    @Test
    public void test10(){
        AuthInfo userInfo = userMapper.login("1","1");
        System.out.println(userInfo);
//        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
//        objectQueryWrapper.eq("username","1");
//        objectQueryWrapper.eq("password","1");
//        User user1 = userMapper.selectOne(objectQueryWrapper);
//        System.out.println(user1);
    }

    @Test
    public void test11(){
        orderUpdateService.updateOrderStatus("202402280609203150",1);
    }

    @Test
    public void test12(){
        QueryWrapper<RepairInfo> repairInfoQueryWrapper = new QueryWrapper<>();
        QueryWrapper<RepairInfo> queryWrapper = repairInfoQueryWrapper.eq("order_id", "202402280609203150");

        RepairInfo repairInfo = repairInfoMapper.selectOne(queryWrapper);

        System.out.println(repairInfo);

    }

    @Test
    public void test13(){

        List<Map<String, Object>> dateIntegerMap = statisticalDataMapper.countWeekOrderById("3");
        dateIntegerMap.forEach(repairInfoMapper->{
            System.out.println(repairInfoMapper.get("orderDate"));
        });

    }

    @Test
    public  void test14(){
        List<Map<String, Object>> maps = statisticalDataService.CountWeekOrderById("3");
        System.out.println(maps);
    }

    @Test
    public void test15(){
//        Object o = redisTemplate.opsForHash().get("User:2", "password");
//        System.out.println(o);
//        boolean b = redisLoginService.queryPassword("2", "");
        redisTemplate.opsForHash().put("User:1","password",1);

//        System.out.println(b);


    }

    @Test
    public  void test16(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",1);
        queryWrapper.eq("password",1);
        boolean exists = userMapper.exists(queryWrapper);
        System.out.println(exists);
    }

    @Test
    public void test17(){
        List<OrderStatus> orders = orderSelectMapper.selectIncompleteOrderById("3");
        System.out.println(orders);
    }

    @Test
    public void test18(){
        UserInfo userInfo = userMapper.selectUserInfoById("1");
        System.out.println(userInfo);
    }

    @Test
    public void test19(){
        List<OrderStatus> orderStatuses = orderSelectService.userFixOrderStatus("3");
        System.out.println(orderStatuses);
    }


    @Test
    public void test20(){
        List<OrderDetailInfo> orderDetailInfos = pageChangeService.orderPageChangeForAdmin(3);
        System.out.println(orderDetailInfos);
    }

    @Test
    public void pageMybatisPlus(){
        Page<Parts> page = new Page<>(1,9);
        Page<Parts> partsPage = partsMapper.selectPage(page, null);
        System.out.println(partsPage);

    }

    @Test
    public void testSearchAddress(){
        QueryWrapper<UserAddress> userAddressQueryWrapper = new QueryWrapper<>();
        userAddressQueryWrapper.eq("user_id",1);

        boolean exists = userAddressInfoMapper.exists(userAddressQueryWrapper);
        System.out.println(exists);
    }

    @Test
    public void testSelectUseParts(){
        String url = minioConfig.getUrl();
        System.out.println(url);
    }

    @Test
    public void testSelectDate(){
        orderSelectService.guaranteeDate("202402280609203150");
    }

    @Test
    public void testDateUtil(){
        DateUtil dateUtil = new DateUtil();
        String s = dateUtil.dateUtilForString();


        System.out.println(s);
    }

    @Test
    public void getComment(){
        Set<Comment> commentsByPostId = redisCommentService.getCommentsByPostId("1781742326280998913");
        System.out.println(commentsByPostId);

    }

    @Test
    public void countComment(){
        redisCommentService.getCommentCountById("1781742326280998913");

    }

    @Test
    public void MapCoordinateTest(){
        List<MapCoordinate> mapCoordinates = mapCoordinate.selectList(new QueryWrapper<>());

        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        HashMap<Object, Object> objectObjectHashMap1 = new HashMap<>();
        JSONObject jsonObject = new JSONObject();

        for (MapCoordinate coordinate : mapCoordinates) {
            objectObjectHashMap1.put(coordinate.getName(),coordinate.getCoordinate());


            jsonObject.put("name",coordinate.getName());
            jsonObject.put("address",coordinate.getAddress());

            String[] split = coordinate.getCoordinate().split(",");
            double[] doublesArray=new double[split.length];
            System.out.println(split.length);

            for (int i = 0; i < split.length; i++) {
                doublesArray[i] = Double.parseDouble(split[i]);
            }




        }





    }
    @Test
    public void testData(){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",0);
        List<Order> orders = orderSelectMapper.selectList(queryWrapper);

        for (Order order : orders) {

            OrderPayInfo orderPayInfo = new OrderPayInfo();
            orderPayInfo.setOrderId(order.getOrder_id());
            orderPayInfo.setStatus(false);
            payInfoMapper.insert(orderPayInfo);
        }
    }

    @Test
    public void testOrder(){
        OrderDetailInfo orderDetailInfo = orderSelectService.selectByOrderId("202402290207234827");
    }

}
