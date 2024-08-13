package com.fixcomputer.service.serviceImpl;


import com.fixcomputer.config.MinioConfig;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfig minioConfig;

    /**
     *
     *
     * 上传二维码
     * @param byteArrayOutputStream
     *
     *
     * */
    public String uploadQRCode(ByteArrayOutputStream byteArrayOutputStream, String order_id){
            try {
                minioClient.putObject(PutObjectArgs.builder()
                        .bucket("qrcode")
                        .object(order_id)
                        .contentType("image/png")
                        .stream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), byteArrayOutputStream.size(), -1)
                        .build()

                );
                return minioConfig.getUrl()+"/qrcode/"+order_id;
            }catch (Exception e){
                log.info(e.toString());
                return "false";
            }
    }


    /**
     *
     * 上传图片
     *
     * */

    public String uploadImage(MultipartFile file,String bucketName){
        String oldFileName = file.getOriginalFilename();

        //获取文件后缀名
        String substring = oldFileName.substring(oldFileName.lastIndexOf("."));

        //生成新的文件名
        String newFileName = UUID.randomUUID().toString()+substring;


        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(newFileName)
                            .stream(file.getInputStream(),file.getSize(),-1)
                            .contentType(file.getContentType())
                            .build()
            );
            return minioConfig.getUrl()+"/"+bucketName+"/"+newFileName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     *
     * 删除图片
     *
     * */
    public boolean deleteObject(String bucketName,String objectName){
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
