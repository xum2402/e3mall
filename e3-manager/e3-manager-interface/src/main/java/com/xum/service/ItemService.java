package com.xum.service;

import com.xum.common.util.E3Result;
import com.xum.comon.pojo.EasyUIDataGridResult;
import com.xum.pojo.Item;
import com.xum.pojo.ItemDesc;

public interface ItemService {

    Item getItemById(Long itemId);

    EasyUIDataGridResult getItemList(Integer pageNum,Integer pageSize);

    E3Result addItem(Item item,String desc);

    /**
     *根据商品id查询商品描述
     * @param id
     * @return
     */
    ItemDesc getItemDescByItemId(Long id);
}
