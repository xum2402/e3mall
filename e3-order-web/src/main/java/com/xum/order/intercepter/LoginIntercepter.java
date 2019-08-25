package com.xum.order.intercepter;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xum.cart.service.CartService;
import com.xum.common.util.CookieUtils;
import com.xum.common.util.E3Result;
import com.xum.pojo.Item;
import com.xum.pojo.User;
import com.xum.sso.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class LoginIntercepter implements HandlerInterceptor {

    @Value("${SSO_URL}")
    private String SSO_URL;

    private ObjectMapper jacksonMapper = new ObjectMapper();

    @Resource
    private TokenService tokenService;

    @Resource
    private CartService cartService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //从cookie中取token
        String token = CookieUtils.getCookieValue(request, "token");

        //token不存在，未登录状态，跳转到sso登录
        if (StringUtils.isBlank(token)){
            response.sendRedirect(SSO_URL+"/page/login?redirect="+request.getRequestURL());
            //拦截
           return false;
        }
        //取到token，已经过期，跳转到登录

        E3Result userByToken = tokenService.getUserByToken(token);
        if (userByToken.getStatus()!=200){
            response.sendRedirect(SSO_URL+"/page/login?redirect="+request.getRequestURL());
            //拦截
            return false;
        }
        //未过期，调用sso服务，获取用户信息,存入request
        User user = (User) userByToken.getData();
        request.setAttribute("user",user);
        //判断cookie中是否有购物车列表
        String cartJson = CookieUtils.getCookieValue(request, "cart", true);
        if(!StringUtils.isBlank(cartJson)){
            //返回带泛型的集合class
            JavaType javaType = jacksonMapper.getTypeFactory().constructParametricType(ArrayList.class, Item.class);

            List<Item> cartList = jacksonMapper.readValue(cartJson, javaType);
//            合并
            cartService.mergeCart(user.getId(),cartList);
        }

        return true;
    }
}
