package com.xum.sso.service;

import com.xum.common.util.E3Result;

/**
 *
 * @author 86182
 */
public interface TokenService {

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    E3Result getUserByToken(String token);
}
