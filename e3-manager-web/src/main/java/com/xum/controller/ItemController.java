package com.xum.controller;

import com.xum.common.util.E3Result;
import com.xum.pojo.Item;
import com.xum.service.ItemService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Resource
    private ItemService itemService;


    /**
     * 查询商品列表
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Object getItemList(Integer page,Integer rows){

        return itemService.getItemList(page,rows);
    }


    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public E3Result addItem(Item item,String desc){
        System.out.println(item.getTitle());
       return itemService.addItem(item, desc);
    }
}
