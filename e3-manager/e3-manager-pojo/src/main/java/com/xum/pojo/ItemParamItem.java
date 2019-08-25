package com.xum.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tb_item_param_item
 * @author 
 */

@Data
public class ItemParamItem implements Serializable {
    private Long id;

    /**
     * 商品ID
     */
    private Long itemId;

    private Date created;

    private Date updated;

    /**
     * 参数数据，格式为json格式
     */
    private String paramData;


}