package com.xum.sso.controller;


import com.xum.common.util.CookieUtils;
import com.xum.common.util.E3Result;
import com.xum.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LonginController {

    @Resource
    private LoginService loginService;

    @RequestMapping("/page/login")
    public String showLogin(String redirect, Model model){
        model.addAttribute("redirect",redirect);
        return "login";
    }

    @PostMapping("user/login")
    @ResponseBody
    public E3Result login(String username, String password, HttpServletRequest request, HttpServletResponse response){

        E3Result e3Result = loginService.userLogin(username, password);
        if (e3Result.getStatus() == 200){
            String token = e3Result.getData().toString();

            CookieUtils.setCookie(request,response,"token",token);
        }

        return e3Result;

    }

}
