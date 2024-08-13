package com.fixcomputer.controller;


import com.alibaba.fastjson2.JSONObject;
import com.alipay.easysdk.factory.Factory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fixcomputer.domain.dto.PayForm;
import com.fixcomputer.domain.entity.MapCoordinate;
import com.fixcomputer.domain.entity.OrderPayInfo;
import com.fixcomputer.domain.entity.QRCode;
import com.fixcomputer.domain.entity.User;
import com.fixcomputer.mapper.MapCoordianteMapper;
import com.fixcomputer.mapper.PayInfoMapper;
import com.fixcomputer.service.OrderSelectService;
import com.fixcomputer.service.PayInfoService;
import com.fixcomputer.service.QRCodeService;
import com.fixcomputer.service.serviceImpl.AliPayService;
import com.fixcomputer.service.serviceImpl.MinioService;
import com.fixcomputer.utils.JwtTokenUtil;
import com.fixcomputer.utils.KdApiSearchUtil;
import com.fixcomputer.utils.RandomCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;


@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiInterface {

    @Autowired
    QRCodeService qrCodeService;

    @Autowired
    MinioService minioService;

    @Autowired
    OrderSelectService orderSelectService;

    @Autowired
    MapCoordianteMapper mapCoordinate;

    @Autowired
    private AliPayService aliPayService;

    @Autowired
    PayInfoMapper payInfoMapper;

    @Autowired
    PayInfoService payInfoService;


    @GetMapping("getpublickey")
    public String publicKey(HttpServletRequest request){
        RandomCode randomCode = new RandomCode();
        String publicKey = randomCode.generateKey(request);

        return publicKey;
    }

    /**
     *
     * 验证token
     *
     * */
    @GetMapping("verifyToken")
    public JSONObject tokenToUsername(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        String header = request.getHeader("token");
        if(!header.isEmpty()&&!header.isBlank()){
            Map stateInfo = jwtTokenUtil.validateToken(header);
            stateInfo.forEach((k,v)->{
                jsonObject.put(k.toString(),v);
            });
            return jsonObject;
        }else {
            jsonObject.put("status",false);
            return jsonObject;
        }

    }

    @GetMapping("/code/{id}")
    public JSONObject getQRCode(@PathVariable String id){
        boolean qrCodeUrl = qrCodeService.existsQRCodeUrl(id);
        JSONObject jsonObject = new JSONObject();
        if(qrCodeUrl){
            Map<String,Object> guaranteeExpire = qrCodeService.isGuaranteeExpire(id);
            QRCode qrCode = qrCodeService.selectQRCodeUrl(id);
            jsonObject.put("qrcode",qrCode);
            jsonObject.put("status",true);
            jsonObject.put("guarantee",guaranteeExpire.get("guarantee"));
            jsonObject.put("remainDate",guaranteeExpire.get("remainDate"));
            return jsonObject;
        }else{
            try {
                //创建二维码
                ByteArrayOutputStream qrCodeImage = qrCodeService.createQRCodeImage(id);
                //存入minio
                String url = minioService.uploadQRCode(qrCodeImage, id);
                if(url !="false"){
                   String guaranteeDate = orderSelectService.guaranteeDate(id);
                    QRCode qrCode = new QRCode();
                    qrCode.setGuaranteeDate(guaranteeDate);
                    qrCode.setUrl(url);
                    qrCode.setOrderId(id);
                    //保存
                    boolean qrCodeSaveUrl = qrCodeService.saveQRCodeUrl(qrCode);
                    Map<String,Object> guaranteeExpire = qrCodeService.isGuaranteeExpire(id);
                    if(qrCodeSaveUrl){
                        jsonObject.put("qrcode",qrCode);
                        jsonObject.put("status",true);
                        jsonObject.put("guarantee",guaranteeExpire.get("guarantee"));

                        jsonObject.put("remainDate",guaranteeExpire.get("remainDate"));
                        System.out.println(guaranteeExpire.get("remainDate"));
                        return jsonObject;
                    }else {
                        jsonObject.put("status",false);
                        return jsonObject;
                    }

                }else {
                    jsonObject.put("status",false);
                    return jsonObject;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);

            }
        }
    }


    @GetMapping("/waybill/{id}")
    public JSONObject waybillInfo(@PathVariable String id){
        KdApiSearchUtil kdApiSearchUtil = new KdApiSearchUtil();
        try {
            //将api封装成json格式
            JSONObject jsonObject = JSONObject.parseObject(kdApiSearchUtil.orderOnlineByJson(id));
            return jsonObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/map")
    public JSONObject mapCoordinate() {
        List<MapCoordinate> mapCoordinates = mapCoordinate.selectList(new QueryWrapper<>());


        JSONObject jsonObject1 = new JSONObject();

        JSONObject jsonObject2 = new JSONObject();

        ArrayList<Object> objects1 = new ArrayList<>();


        for (MapCoordinate coordinate : mapCoordinates) {
            //放入一个数组
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", coordinate.getName());
            jsonObject.put("address", coordinate.getAddress());
            objects1.add(jsonObject);


            //转为一个数组
            String[] split = coordinate.getCoordinate().split(",");

            ArrayList<Object> objects = new ArrayList<>();

            for (String s : split) {
                objects.add(s);
            }

            jsonObject1.put(coordinate.getName(),objects);

        }
        //整合
        jsonObject2.put("data",objects1);
        jsonObject2.put("geoCoordMap",jsonObject1);

        return jsonObject2;



    }

    @PostMapping("/pay")
    public String toPay(@RequestBody PayForm payform){

        try {
            QueryWrapper<OrderPayInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_id",payform.getOrder_id());
            OrderPayInfo orderPayInfo = payInfoMapper.selectOne(queryWrapper);
            System.out.println(orderPayInfo);
            String form = aliPayService.toPay("维修"+payform.getCategory(),orderPayInfo.getTotalAmount(),orderPayInfo.getOrderId());

            return form;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @GetMapping("/payDone")
    public void  notify (@RequestParam Map<String,String> ms,HttpServletResponse response) throws Exception {
        System.out.println(ms);

        boolean aliPayBoolean = Factory.Payment.Common().verifyNotify(ms);

        if(!aliPayBoolean){
            fowardIndex(false,response);
        }
        String order_id = ms.get("out_trade_no");
        String create_date = ms.get("timestamp");

        boolean b = payInfoService.updateOrderPayInfo(order_id,create_date);
        fowardIndex(b,response);


    }

    @GetMapping("/test")
    public void fowardIndex(boolean isPay,HttpServletResponse response){
        try {
            if(isPay){
                response.sendRedirect("http://localhost:3000/home/myOrder");
            }else{
                response.getWriter().write("false");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }




}
