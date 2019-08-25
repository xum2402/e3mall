package com.xum.search.service.impl;

import com.xum.common.util.E3Result;
import com.xum.comon.pojo.SearchItem;
import com.xum.search.mapper.ItemMapper;
import com.xum.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;
    @Override
    public E3Result importAllItems() {
//        查询商品列表
        List<SearchItem> itemList = itemMapper.getItemList();
//        遍历商品列表
        try {
            for (SearchItem searchItem : itemList) {
                //                创建文档对象
                SolrInputDocument document = new SolrInputDocument();
    //            向文档对象中添加域
                document.addField("id",searchItem.getId());
                document.addField("item_title",searchItem.getTitle());
                document.addField("item_sell_point",searchItem.getSell_point());
                document.addField("item_price",searchItem.getPrice());
                document.addField("item_image",searchItem.getImage());
                document.addField("item_category_name",searchItem.getCategory_name());
    //            把文档对象写入索引库
                solrServer.add(document);
            }
//        提交
            solrServer.commit();
            //        返回导入成功
            return E3Result.ok();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return E3Result.build(500,"数据导入异常");
    }
}
