package com.xum.sso.service.impl;

import com.xum.common.util.E3Result;
import com.xum.mapper.UserMapper;
import com.xum.pojo.User;
import com.xum.pojo.UserExample;
import com.xum.sso.service.LoginService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String,User> redisTemplate;

    @Override
    public E3Result userLogin(String userName, String password) {

        //根据用户名查询用户信息
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(userName);

        List<User> users = userMapper.selectByExample(example);
        if (users == null || users.size() == 0){
            return E3Result.build(400,"用户名或密码错误");
        }
        User user = users.get(0);
        //将输入的密码加秘后与数据库的用户信息比对
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!md5.equals(user.getPassword())){
            return E3Result.build(400,"用户名或密码错误");
        }

        String token = UUID.randomUUID().toString();
        user.setPassword(null);

        redisTemplate.opsForValue().set("SESSION:"+token,user,24, TimeUnit.HOURS);

        return E3Result.ok(token);
    }
}
