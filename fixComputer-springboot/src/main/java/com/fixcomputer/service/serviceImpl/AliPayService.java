package com.fixcomputer.service.serviceImpl;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class AliPayService {

    public String toPay(String subject , Double money,String order_id) throws Exception {
        AlipayTradePagePayResponse pay = Factory.Payment.Page().pay(subject, order_id,
                String.valueOf(money), "http://localhost:8080/api/payDone");
        String payForm = null;

        if(ResponseChecker.success(pay)){
            payForm = pay.getBody();
        }

        return payForm;
    }


    /***
     *
     * 生成外部订单号
     *
     */
    private String generateTradeNo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String tradeNo = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
        return tradeNo;
    }

}
