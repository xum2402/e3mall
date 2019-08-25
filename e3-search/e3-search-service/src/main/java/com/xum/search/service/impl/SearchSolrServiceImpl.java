package com.xum.search.service.impl;

import com.xum.comon.pojo.SearchResult;
import com.xum.search.service.SearchSolrService;
import com.xum.search.solrdao.SearchDao;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchSolrServiceImpl implements SearchSolrService {

    @Autowired
    private SearchDao searchDao;
    @Override
    public SearchResult searchSolr(String keyword, Integer page, Integer rows) {
        //创建查询
        SolrQuery query = new SolrQuery();
        //查询条件
        query.setQuery(keyword);
        query.setStart((page-1)*rows);
        query.setRows(rows);
        //设置搜索域
        query.set("df","item_title");
        //开启高亮
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");

        //执行查询
        SearchResult searchResult = null;
        try {
            searchResult = searchDao.search(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }

        //计算总页数
        Long recordCount = searchResult.getRecordCount();
        int totalPage = (int) (recordCount/rows);
        if (recordCount%rows>0){
            totalPage++;
        }
        searchResult.setTotalPages(totalPage);
        return searchResult;
    }
}
