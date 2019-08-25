package com.xum.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xum.common.util.E3Result;
import com.xum.common.util.IDUtils;
import com.xum.comon.pojo.EasyUIDataGridResult;
import com.xum.mapper.ItemDescMapper;
import com.xum.pojo.Item;
import com.xum.pojo.ItemDesc;
import com.xum.pojo.ItemExample;
import com.xum.service.ItemService;
import com.xum.mapper.ItemMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class ItemServiceImpl implements ItemService {


    @Resource
    private JmsTemplate jmsTemplate;

    @Resource
    private Destination destinationTopic;

    @Resource
    private ItemMapper itemMapper;

    @Resource
    private ItemDescMapper itemDescMapper;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;


    /**
     *
     * @param pageNum 页数
     * @param pageSize 条数
     * @return
     */
    @Override
    public EasyUIDataGridResult getItemList(Integer pageNum, Integer pageSize) {

        ItemExample itemExample = new ItemExample();

        PageHelper.startPage(pageNum,pageSize);
        Page<Item> items = (Page<Item>) itemMapper.selectByExample(itemExample);

        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(items.getTotal());
        result.setRows(items);
        return result;
    }

    /**
     * 添加商品
     * @param item
     * @param desc
     * @return
     */
    @Override
    public E3Result addItem(Item item, String desc) {
        //生成商品Id
        final long itemId = IDUtils.genItemId();
        //补全商品信息
        item.setId(itemId);
        //1-正常,2-下架,3-删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        itemMapper.insert(item);

        //商品描述表
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insert(itemDesc);

        //发送商品消息
        jmsTemplate.send(destinationTopic, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(itemId + "");
                return textMessage;
            }
        });

        return E3Result.ok();
    }


    //商品基本信息
    @Override
    public Item getItemById(Long itemId) {
        Item item = null;
        try {
            item = (Item) redisTemplate.opsForValue().get("ITEM_INFO:" + itemId + ":BASE");
            if (item!=null){
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

         item = itemMapper.selectByPrimaryKey(itemId);
        try {
            redisTemplate.opsForValue().set("ITEM_INFO:"+itemId+":BASE",item,1,TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }


    //商品描述
    @Override
    public ItemDesc getItemDescByItemId(Long id) {
        ItemDesc itemDesc = null;
        try {
            itemDesc = (ItemDesc) redisTemplate.opsForValue().get("ITEM_INFO:" + id + ":DESC");
            if (itemDesc!=null){
                return itemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        itemDesc = itemDescMapper.selectByPrimaryKey(id);
        try {
            redisTemplate.opsForValue().set("ITEM_INFO:"+id+":DESC",itemDesc,1, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc;
    }
}
