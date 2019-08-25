package com.xum.item.controller;

import com.xum.item.pojo.ItemSub;
import com.xum.pojo.Item;
import com.xum.pojo.ItemDesc;
import com.xum.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 商品详情
 */

@Controller
public class ItemController {

    @Resource
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model){
        Item item = itemService.getItemById(itemId);
        ItemSub itemSub = new ItemSub(item);
        ItemDesc itemDesc = itemService.getItemDescByItemId(itemId);
        model.addAttribute("item",itemSub);
        model.addAttribute("itemDesc",itemDesc);

        return "item";

    }
}
