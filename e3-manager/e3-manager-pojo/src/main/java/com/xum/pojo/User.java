package com.xum.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tb_user
 * @author 
 */

@Data
public class User implements Serializable {
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码，加密存储
     */
    private String password;

    /**
     * 注册手机号
     */
    private String phone;

    /**
     * 注册邮箱
     */
    private String email;

    private Date created;

    private Date updated;


}