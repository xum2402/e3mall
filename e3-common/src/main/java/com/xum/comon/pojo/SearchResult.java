package com.xum.comon.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索框的查询结果
 *
 * @author 86182
 */
@Data
public class SearchResult implements Serializable {
    //记录数
    private Long recordCount;
    //总页数
    private Integer totalPages;
    //查询结果
    private List<SearchItem> itemList;

}
