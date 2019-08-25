package com.xum.search.message;

import com.xum.comon.pojo.SearchItem;
import com.xum.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;


/**
 * 监听商品添加消息,同步商品到索引库
 */
public class ItemAddMessageListener implements MessageListener {

    @Resource
    private SolrServer solrServer;

    @Resource
    private ItemMapper itemMapper;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            //从消息队列中取出商品id
            String text = textMessage.getText();
            Long itemId = Long.parseLong(text);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            SearchItem searchItem = itemMapper.getSearchItemById(itemId);

            SolrInputDocument document = new SolrInputDocument();
            //            向文档对象中添加域
            document.addField("id",searchItem.getId());
            document.addField("item_title",searchItem.getTitle());
            document.addField("item_sell_point",searchItem.getSell_point());
            document.addField("item_price",searchItem.getPrice());
            document.addField("item_image",searchItem.getImage());
            document.addField("item_category_name",searchItem.getCategory_name());

            try {
                solrServer.add(document);
                solrServer.commit();
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
