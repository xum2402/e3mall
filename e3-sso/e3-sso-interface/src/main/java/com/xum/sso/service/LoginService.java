package com.xum.sso.service;

import com.xum.common.util.E3Result;

public interface LoginService {

    E3Result userLogin(String userName,String password);
}
