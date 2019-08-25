package com.xum.comon.pojo;

import lombok.Data;

import java.io.Serializable;


/**
 * @author 86182
 * 节点树
 */
@Data
public class EasyUITreeNode implements Serializable {

    private Long id;
    private String text;
    private String state;
}
