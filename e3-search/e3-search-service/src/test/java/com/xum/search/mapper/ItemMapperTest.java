package com.xum.search.mapper;

import com.xum.comon.pojo.SearchItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-dao.xml")
public class ItemMapperTest {

    @Resource
    private ItemMapper itemMapper;

    @Test
    public void getSearchItemById() {
        SearchItem searchItem = itemMapper.getSearchItemById(1345596L);
        Assert.assertNotNull(searchItem);
    }
}