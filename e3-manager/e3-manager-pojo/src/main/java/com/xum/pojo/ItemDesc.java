package com.xum.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tb_item_desc
 * @author 
 */

@Data
public class ItemDesc implements Serializable {
    /**
     * 商品ID
     */
    private Long itemId;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;

    /**
     * 商品描述
     */
    private String itemDesc;


}