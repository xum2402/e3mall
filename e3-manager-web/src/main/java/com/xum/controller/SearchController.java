package com.xum.controller;

import com.xum.common.util.E3Result;
import com.xum.search.service.SearchItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 添加索引库
 *
 * @author 86182
 */
@RestController
public class SearchController {

    @Resource
    private SearchItemService searchService;

    @RequestMapping(value = "/index/item/import")
    public E3Result importItemList(){
        return searchService.importAllItems();
    }
}
