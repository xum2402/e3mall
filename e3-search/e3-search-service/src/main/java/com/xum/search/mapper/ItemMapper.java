package com.xum.search.mapper;


import com.xum.comon.pojo.SearchItem;

import java.util.List;

/**
 * 查询索引库需要的商品信息
 * @author 86182
 */
public interface ItemMapper {

    List<SearchItem> getItemList();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SearchItem getSearchItemById(Long id);
}
