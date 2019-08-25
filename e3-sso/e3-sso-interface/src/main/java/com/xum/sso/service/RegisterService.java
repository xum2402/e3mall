package com.xum.sso.service;

import com.xum.common.util.E3Result;
import com.xum.pojo.User;

public interface RegisterService {

    /**
     * 校验数据的合法性
     * @param param 参数
     * @param type 参数类型:1,用户名 2,手机号 3,邮箱
     * @return
     */
    E3Result checkData(String param,Integer type);

    E3Result register(User user);
}
