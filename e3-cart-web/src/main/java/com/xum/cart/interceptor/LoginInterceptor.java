package com.xum.cart.interceptor;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xum.common.util.CookieUtils;
import com.xum.common.util.E3Result;
import com.xum.pojo.User;
import com.xum.sso.service.TokenService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private TokenService tokenService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //前处理,执行handler之前执行此方法
        //返回true,放行，false,拦截

//        从cookie中取token
        String token = CookieUtils.getCookieValue(request, "token");
//        没有token，未登录状态，直接放行
        if (StringUtils.isBlank(token)){
            return true;
        }
//        取到token，调用sso服务，根据token获取用户信息
        E3Result userByToken = tokenService.getUserByToken(token);
//        没有取到用户信息,登录过期，直接放行
        if (userByToken.getStatus()!=200){
         return true;
        }
//        取到用户信息，登录状态
        User user = (User) userByToken.getData();
//        将用户信息放到request中，在controller中判断是否有user信息
        request.setAttribute("user",user);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //hanler执行之后,返回modelandview之前
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //完成处理后可以在此处理异常
    }
}
