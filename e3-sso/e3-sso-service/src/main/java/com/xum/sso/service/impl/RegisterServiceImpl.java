package com.xum.sso.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xum.common.util.E3Result;
import com.xum.mapper.UserMapper;
import com.xum.pojo.User;
import com.xum.pojo.UserExample;
import com.xum.sso.service.RegisterService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Resource
    private UserMapper userMapper;


    @Override
    public E3Result checkData(String param, Integer type) {

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        if (type == 1){
            criteria.andUsernameEqualTo(param);
        }else if (type == 2){
            criteria.andPhoneEqualTo(param);
        }else if (type == 3){
            criteria.andEmailEqualTo(param);
        }else{
            return E3Result.build(400,"数据类型错误");
        }

        List<User> users = userMapper.selectByExample(example);

        if (users!= null && users.size() > 0){
            return E3Result.ok(false);
        }

        return E3Result.ok(true);
    }

    @Override
    public E3Result register(User user) {
            //数据有效性校验
        if(StringUtils.isBlank(user.getUsername())
                || StringUtils.isBlank(user.getPassword())
                || StringUtils.isBlank(user.getPhone())){
            return E3Result.build(400,"用户数据不完整");
        }


        user.setCreated(new Date());
        user.setUpdated(new Date());

//        对密码进行MD5加秘
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);
        userMapper.insert(user);

        return E3Result.ok();
    }
}
