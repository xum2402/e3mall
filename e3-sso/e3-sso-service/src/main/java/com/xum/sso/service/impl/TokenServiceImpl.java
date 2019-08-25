package com.xum.sso.service.impl;

import com.xum.common.util.E3Result;
import com.xum.pojo.User;
import com.xum.sso.service.TokenService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private RedisTemplate<String, User> redisTemplate;


    @Override
    public E3Result getUserByToken(String token) {
        //根据token到redis中获取用户信息
        User user = redisTemplate.opsForValue().get("SESSION:"+token);
        //取不到用户信息，登录过期，返回登录过期
        if (user == null){
            return E3Result.build(201,"用户登录过期");
        }
        //取到了用户信息，更新token过期时间
        redisTemplate.expire(token,24, TimeUnit.HOURS);


        return E3Result.ok(user);
    }
}
