package com.fixcomputer.controller;


import com.alibaba.fastjson2.JSONObject;
import com.fixcomputer.service.serviceImpl.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImagesModule {

    @Autowired
    MinioService minioService;

    /**
     *
     * 保存图片
     *
     * */
    @PostMapping
    public JSONObject saveImages(@RequestParam("images")MultipartFile image){
        JSONObject jsonObject = new JSONObject();

        String tieba = minioService.uploadImage(image, "tieba");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("url",tieba);

        if(!tieba.isEmpty()){
            jsonObject.put("errno",0);
            jsonObject.put("data",hashMap);
            return jsonObject;
        }else {
            jsonObject.put("errno",1);
            return jsonObject;
        }


    }

    /**
     *
     * 删除图片
     *
     * */
    @DeleteMapping
    public void deleteImageFromMinio(@RequestBody List<String> images){


        images.forEach(imagesUrl->{
                String[] parts = imagesUrl.split("/");
                String bucketName = parts[3];
                String objectName = parts[4];

                minioService.deleteObject(bucketName, objectName);
            });


        }





}
