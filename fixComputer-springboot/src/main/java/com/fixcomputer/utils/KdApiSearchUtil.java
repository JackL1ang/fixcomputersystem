package com.fixcomputer.utils;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson2.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;

public class KdApiSearchUtil {

        //用户ID，快递鸟提供，注意保管，不要泄漏
        private String EBusinessID="1826976";//即用户ID，登录快递鸟官网会员中心获取 https://www.kdniao.com/UserCenter/v4/UserHome.aspx
        //API key，快递鸟提供，注意保管，不要泄漏
        private String ApiKey="edacda4c-d5a0-4d15-8118-288ec8fcdb90";//即API key，登录快递鸟官网会员中心获取 https://www.kdniao.com/UserCenter/v4/UserHome.aspx
        //请求url, 正式环境地址
        private String ReqURL="https://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";







        //即时查询接口
        public String orderOnlineByJson(String LogisticCode) throws Exception{
            // 组装应用级参数
            String RequestData= "{"+
                    "'OrderCode':'',"+
                    "'ShipperCode':'YTO',"+
                    "'LogisticCode':'"+LogisticCode+"'"+
                    "}";
            // 组装系统级参数
            Map<String,String> params = new HashMap<>();
            params.put("RequestType", "1002");//免费即时查询接口指令1002/在途监控即时查询接口指令8001/地图版即时查询接口指令8003
            params.put("EBusinessID", EBusinessID);
            params.put("RequestData", urlEncoder(RequestData, "UTF-8"));
            String dataSign=encrypt(RequestData, ApiKey, "UTF-8");
            params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
            params.put("DataType", "2");


            // 以form表单形式提交post请求，post请求体中包含了应用级参数和系统级参数
            String result=sendPost(ReqURL, params);

            //根据公司业务处理返回的信息......
            return result;
        }


        /**
         * MD5加密
         * str 内容
         * charset 编码方式
         * @throws Exception
         */
        @SuppressWarnings("unused")
        private String MD5(String str,String charset) throws Exception {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes(charset));
            byte[] result = md.digest();
            StringBuffer sb = new StringBuffer(32);
            for (int i = 0; i < result.length; i++) {
                int val = result[i] & 0xff;
                if (val <= 0xf) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(val));
            }
            return sb.toString().toLowerCase();
        }

        /**
         * base64编码
         * str 内容
         * charset 编码方式
         * @throws UnsupportedEncodingException
         */
        private String base64(String str, String charset) throws UnsupportedEncodingException {
            String s = Base64.encodeBase64URLSafeString(str.getBytes(charset))+"%3d";

            System.out.println(s);
            return s;
        }

        @SuppressWarnings("unused")
        private String urlEncoder(String str, String charset) throws UnsupportedEncodingException{
            String result = URLEncoder.encode(str, charset);
            System.out.println("result--------->"+result);
            return result;
        }

        /**
         * 电商Sign签名生成
         * content 内容
         * keyValue ApiKey
         * charset 编码方式
         * @throws UnsupportedEncodingException ,Exception
         * @return DataSign签名
         */
        @SuppressWarnings("unused")
        private  String encrypt (String content,String keyValue,String charset) throws UnsupportedEncodingException, Exception
        {
            if (keyValue != null)
            {
                return base64(MD5(content + keyValue, charset), charset);
            }
            return base64(MD5(content, charset), charset);
        }

        /**
         * 向指定 URL 发送POST方法的请求
         * url 发送请求的 URL
         * params 请求的参数集合
         * @return 远程资源的响应结果
         */
        @SuppressWarnings("unused")
        private String sendPost(String url, Map<String,String> params) {
            OutputStreamWriter out = null;
            BufferedReader in = null;
            StringBuilder result = new  StringBuilder();
            try {
                URL realUrl = new URL(url);
                HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
                // 发送POST请求必须设置如下两行
                conn.setDoOutput(true);
                conn.setDoInput(true);
                // POST方法
                conn.setRequestMethod("POST");
                // 设置通用的请求属性
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.connect();
                // 获取URLConnection对象对应的输出流
                out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                // 发送请求参数
                if (params != null) {
                    StringBuilder param = new    StringBuilder();
                    for (Map.Entry<   String,    String> entry : params.entrySet()) {
                        if(param.length()>0){
                            param.append("&");
                        }
                        param.append(entry.getKey());
                        param.append("=");
                        param.append(entry.getValue());
                        System.out.println(entry.getKey()+":"+entry.getValue());
                    }
                    System.out.println("param:"+param.toString());
                    out.write(param.toString());
                }
                // flush输出流的缓冲
                out.flush();
                // 定义BufferedReader输入流来读取URL的响应

                in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line;
                while ((line = in.readLine()) != null) {

                    result.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //使用finally块来关闭输出流、输入流
            finally{
                try{
                    if(out!=null){
                        out.close();
                    }
                    if(in!=null){
                        in.close();
                    }
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
            }
            return result.toString();
        }
    }




