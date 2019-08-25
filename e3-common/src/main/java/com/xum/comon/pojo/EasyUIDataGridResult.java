package com.xum.comon.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author 86182
 *分页
 */
@Data
public class EasyUIDataGridResult implements Serializable {

    private Long total;
    /**
     * 查询的列表List
     */
    private List rows;
}
