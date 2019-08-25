package com.xum.sso.controller;

import com.xum.common.util.E3Result;
import com.xum.pojo.User;
import com.xum.sso.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class RegisterController {

    @Resource
    private RegisterService registerService;


    @RequestMapping("/page/register")
    public String showRegister(){

        return "register";
    }

    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public E3Result checkData(@PathVariable String param,@PathVariable Integer type){
        return registerService.checkData(param,type);
    }

    @PostMapping("/user/register")
    @ResponseBody
    public E3Result register(User user){
        return registerService.register(user);
    }
}
