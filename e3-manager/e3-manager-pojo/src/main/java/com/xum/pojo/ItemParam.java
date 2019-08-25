package com.xum.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tb_item_param
 * @author 
 */

@Data
public class ItemParam implements Serializable {
    private Long id;

    /**
     * 商品类目ID
     */
    private Long itemCatId;

    private Date created;

    private Date updated;

    /**
     * 参数数据，格式为json格式
     */
    private String paramData;


}