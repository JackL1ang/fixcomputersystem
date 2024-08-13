package com.fixcomputer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixcomputer.domain.entity.QRCode;
import com.google.zxing.WriterException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public interface QRCodeService extends IService<QRCode> {

    ByteArrayOutputStream createQRCodeImage(String order_id) throws WriterException, IOException;

    boolean existsQRCodeUrl(String order_id);

    QRCode selectQRCodeUrl(String order_id);

    boolean saveQRCodeUrl(QRCode qrCode);

    Map<String,Object> isGuaranteeExpire(String order_id);
}
