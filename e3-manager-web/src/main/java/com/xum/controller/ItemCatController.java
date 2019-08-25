package com.xum.controller;

import com.xum.comon.pojo.EasyUITreeNode;
import com.xum.service.ItemCatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ItemCatController {

    @Resource
    private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")

    public List<EasyUITreeNode> getItemCatList(Long id){
        //默认值
        if (id==null){
            id = 0L;
        }
        return itemCatService.getItemCatList(id);
    }

}
