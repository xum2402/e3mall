package com.xum.search.controller;


import com.xum.comon.pojo.SearchResult;
import com.xum.search.service.SearchSolrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class SearchController {
    @Value("${SEARCH_ROWS}")
    private Integer ROWS;
    @Resource
    private SearchSolrService searchSolrService;
    @RequestMapping("/search")
    public String searchSolr(String keyword, @RequestParam(defaultValue = "1") Integer page, Model model){


        SearchResult searchResult = searchSolrService.searchSolr(keyword, page, ROWS);
        model.addAttribute("query",keyword);
        model.addAttribute("totalPages",searchResult.getTotalPages());
        model.addAttribute("page",page);
        model.addAttribute("recourdCount",searchResult.getRecordCount());
        model.addAttribute("itemList",searchResult.getItemList());
        return "search";
    }
}
