package com.fixcomputer.service.serviceImpl;


import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixcomputer.domain.entity.CompleteDate;
import com.fixcomputer.domain.entity.QRCode;
import com.fixcomputer.mapper.OrderSelectMapper;
import com.fixcomputer.mapper.QRCodeMapper;
import com.fixcomputer.service.QRCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class QRCodeServiceImpl extends ServiceImpl<QRCodeMapper, QRCode> implements QRCodeService {


    @Autowired
    private QRCodeMapper qrCodeMapper;

    @Autowired
    private OrderSelectMapper orderSelectMapper;


    public ByteArrayOutputStream createQRCodeImage(String order_id) throws WriterException, IOException {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

                BitMatrix encode = qrCodeWriter.encode(order_id, BarcodeFormat.QR_CODE, 250, 250);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                MatrixToImageWriter.writeToStream(encode,"PNG",byteArrayOutputStream);
                return byteArrayOutputStream;


    }

    public boolean existsQRCodeUrl(String order_id){
        QueryWrapper<QRCode> qrCodeQueryWrapper = new QueryWrapper<>();
        qrCodeQueryWrapper.eq("order_id",order_id);
        boolean exists = qrCodeMapper.exists(qrCodeQueryWrapper);
        return exists;
    }

    @Override
    public QRCode selectQRCodeUrl(String order_id) {
        QRCode qrCode = qrCodeMapper.selectById(order_id);
        return qrCode;
    }


    /**
     *
     * 保存二维码信息
     *
     *
     * */
    @Override
    public boolean saveQRCodeUrl(QRCode qrCode) {

        int insert = qrCodeMapper.insert(qrCode);

        if(insert==1){
            return true;
        }else{
            return false;
        }

    }


    /**
     *
     * 查询是否已过保修
     *
     * */
    public Map<String,Object> isGuaranteeExpire(String order_id){
        QueryWrapper<QRCode> qrCodeQueryWrapper = new QueryWrapper<>();
        qrCodeQueryWrapper.eq("order_id",order_id);
        QRCode qrCode = qrCodeMapper.selectOne(qrCodeQueryWrapper);
        HashMap<String, Object> map = new HashMap<>();

        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = simpleDateFormat.parse(qrCode.getGuaranteeDate());

            if(nowDate.before(date)){
                long abs = Math.abs(date.getTime() - nowDate.getTime());
                double days = abs/(24*60*60*1000);
                map.put("remainDate",days);
                map.put("guarantee","1");
                return map;
            }else{
                map.put("guarantee","0");
                return map;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }


}
