package com.xum.search.service;

import com.xum.comon.pojo.SearchResult;

/**
 * 查询solr库莱获得商品列表
 */
public interface SearchSolrService {

    SearchResult searchSolr(String keyword,Integer page,Integer rows);
}
