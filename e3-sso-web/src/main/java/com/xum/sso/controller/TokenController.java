package com.xum.sso.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xum.common.util.E3Result;
import com.xum.sso.service.TokenService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TokenController {

    @Resource
    private TokenService tokenService;

    @RequestMapping(value = "/user/token/{token}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getUserByToken(@PathVariable String token,String callback){
        E3Result result = tokenService.getUserByToken(token);

        //响应结果之前，判断是否为jsonp请求
        if (!StringUtils.isBlank(callback)){
            try {
              return callback + "("+ new ObjectMapper().writeValueAsString(result)+");";

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
