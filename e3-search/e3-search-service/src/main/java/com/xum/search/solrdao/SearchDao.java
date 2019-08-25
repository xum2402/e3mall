package com.xum.search.solrdao;

import com.xum.comon.pojo.SearchItem;
import com.xum.comon.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;

    public SearchResult search(SolrQuery query) throws SolrServerException {
        //根据查询条件查询索引库
        QueryResponse queryResponse = solrServer.query(query);
        //取查询结果
        SolrDocumentList documentList = queryResponse.getResults();

        SearchResult searchResult = new SearchResult();
        //取总记录数
        long numFound = documentList.getNumFound();
        searchResult.setRecordCount(numFound);

        //取出高亮列表
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        //添加商品列表
        List<SearchItem> searchItems = new ArrayList<>();
        for (SolrDocument solrDocument : documentList) {
            SearchItem item = new SearchItem();
            item.setId((String) solrDocument.get("id"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));
            item.setImage((String) solrDocument.get("item_image"));
            item.setCategory_name((String) solrDocument.get("item_image"));
            item.setPrice((Long) solrDocument.get("item_price"));

            //取高亮显示的标题
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            if(list!=null && list.size()>0){
                //如果有高亮标题就设置高亮
                item.setTitle(list.get(0));
            }else{
//                没有高亮就设置原始的
                item.setTitle((String) solrDocument.get("item_title"));
            }

            searchItems.add(item);
        }
        searchResult.setItemList(searchItems);
        //页数未设置
        return searchResult;
    }
}
