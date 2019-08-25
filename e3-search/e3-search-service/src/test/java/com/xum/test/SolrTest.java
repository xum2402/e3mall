package com.xum.test;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

public class SolrTest {


    @Test
    public void addDocument() throws IOException, SolrServerException {
//        创建一个连接
        SolrServer solrServer = new HttpSolrServer("http://192.168.100.100:8080/solr/collection1");

        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","doc01");
        document.addField("item_title","测试商品01");
        document.addField("item_price",1000);
        solrServer.add(document);
        solrServer.commit();
    }

    @Test
    public void deleteDocument() throws IOException, SolrServerException {
//        创建一个连接
        SolrServer solrServer = new HttpSolrServer("http://192.168.100.100:8080/solr/collection1");


        solrServer.deleteByQuery("*:*");

        solrServer.commit();

    }
}
