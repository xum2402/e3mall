package com.xum.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xum.common.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PictureController {

    /**
     * 读取配置文件属性
     */
    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;
                                                    /**标识响应类型*/
    @RequestMapping(value = "/pic/upload",produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    public String uploadFile(MultipartFile uploadFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //把图片上传到图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");

            //取文件扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.indexOf(".") + 1);
            //获取上传后的url
            String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            url = IMAGE_SERVER_URL + url;
            //  上传结果封装到map中返回
            Map result = new HashMap<>();
            result.put("error", 0);
            result.put("url", url);
            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            e.printStackTrace();
            Map result = new HashMap<>();
            result.put("error", 1);
            result.put("url", "图片上传失败");
            String resultString = null;
            try {
                resultString = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
            return resultString;
        }
    }

}
